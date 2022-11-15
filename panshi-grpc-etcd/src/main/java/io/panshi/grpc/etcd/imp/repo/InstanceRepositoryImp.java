package io.panshi.grpc.etcd.imp.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.common.exception.EtcdException;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.lease.LeaseRevokeResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.event.WatchInstanceEvent;
import io.panshi.grpc.etcd.api.event.WatchInstanceListener;
import io.panshi.grpc.etcd.api.exception.ErrorCode;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class InstanceRepositoryImp extends AbstractRepository implements InstanceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRepositoryImp.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 3;
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    protected InstanceRepositoryImp(Config config) throws PanshiException {
        super(config);
    }

    @Override
    public void putInstanceInfo(Instance instance) throws PanshiException {
        KV kvClient = this.getRepoClient().getKVClient();
        ByteSequence path = ByteSequence.from( String.format("%s/%s/%s/%s",ROOT_PATH,instance.getNamespace(),
                instance.getSet(),instance.getService()),StandardCharsets.UTF_8);
        try {
            ByteSequence value = ByteSequence.from(MAPPER.writeValueAsString(instance)
                    .getBytes(StandardCharsets.UTF_8));
            PutOption option = PutOption.newBuilder().withLeaseId(this.getClientGlobalLeaseId())
                    .build();
            CompletableFuture<PutResponse> future = kvClient.put(path, value, option);
            PutResponse response = future.get(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            LOGGER.info("put instance info success {} ",response);
        } catch (JsonProcessingException e) {
            LOGGER.error("mapper map to json bytes error {} ",e.getMessage());
            throw PanshiException.newError(
                    ErrorCode.INVALID_INPUT,String.format("invalid args %s", instance)
            );
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            LOGGER.error("put instance info exception {} instance {} ",
                    e.getMessage(), instance);
            throw PanshiException.newError(ErrorCode.UNKNOWN_ERROR,e.getMessage());
        }
    }

    @Override
    public List<Instance> getInstanceList(String namespace, String set, String service) {
        String path = String.format("%s/%s/%s/%s", ROOT_PATH,namespace,set,service);
        return getInstanceList(path,false);
    }

    private List<Instance>  getInstanceList(String path,boolean withPrefix){
        KV kvClient = this.getRepoClient().getKVClient();
        ByteSequence prefixKey = ByteSequence.from(
                path, StandardCharsets.UTF_8);
        GetOption option = GetOption.newBuilder().isPrefix(withPrefix)
                .withSortField(GetOption.SortTarget.KEY)
                .build();
        CompletableFuture<GetResponse> future = kvClient.get(prefixKey, option);
        try {
            GetResponse response = future.get(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            List<KeyValue> keyValueList = response.getKvs();
            return keyValueList.stream()
                    .map(kv->{
                        try {
                            return MAPPER.readValue(
                                    kv.getValue().getBytes(),
                                    new TypeReference<Instance>() {
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                            LOGGER.error("mapper convert data to instance error {} key = {} value = {}",
                                    e.getMessage(), kv.getKey().toString(StandardCharsets.UTF_8),
                                    kv.getValue().toString(StandardCharsets.UTF_8));
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            LOGGER.error("get instance list exception {} ",e.getMessage());
            return new ArrayList<>();
        }catch (EtcdException e){
            e.printStackTrace();
            LOGGER.error("get instance list exception etcd code {} , message {} ",
                    e.getErrorCode(),e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Instance> getInstanceList() {
        return getInstanceList(ROOT_PATH,true);
    }

    @Override
    public void watchInstanceChangeStream( WatchInstanceListener listener  ) {
        ByteSequence key = ByteSequence.from(InstanceRepository.ROOT_PATH,
                StandardCharsets.UTF_8);
        WatchOption option = WatchOption.newBuilder().isPrefix(true).build();
        this.getRepoClient().getWatchClient()
                .watch(key, option,
                        response -> {
                            List<WatchEvent> events = response.getEvents();
                            for (WatchEvent event : events) {
                                WatchInstanceEvent instanceEvent = new WatchInstanceEvent();
                                if ( WatchEvent.EventType.PUT . equals( event.getEventType() )) {
                                    instanceEvent.setType(WatchInstanceEvent.Type.REGISTER);
                                }else if ( WatchEvent.EventType.DELETE . equals( event.getEventType() )) {
                                    instanceEvent.setType(WatchInstanceEvent.Type.REGISTER);
                                }else{
                                    continue; // TODO
                                }
                                listener.handle(instanceEvent);
                            }
                        },
                        throwable -> {
                            LOGGER.info("handle instance change stream event error {} ",
                                    throwable.getMessage());
                            throwable.printStackTrace();
                        });
    }

    @Override
    public void deleteInstance(Instance instance) throws PanshiException {
        if (this.getClientGlobalLeaseId() == 0L ){
            throw new PanshiException(ErrorCode.DELETE_INSTANCE_FAILED,
                    "already delete instance info");
        }
        Lease leaseClient = this.getRepoClient().getLeaseClient();
        CompletableFuture<LeaseRevokeResponse> future = leaseClient.revoke(this.getClientGlobalLeaseId());
        try {
            LeaseRevokeResponse response = future.get(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            LOGGER.info("delete instance info success {}",response);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            LOGGER.error("revoke lease exception {} ",e.getMessage());
            throw PanshiException.newError(ErrorCode.UNKNOWN_ERROR,e.getMessage());
        }catch (EtcdException e){
            e.printStackTrace();
            if (io.etcd.jetcd.common.exception.ErrorCode.NOT_FOUND.equals(e.getErrorCode())){
                LOGGER.error("revoke lease error, leaseId [{}] not found ",
                        this.getClientGlobalLeaseId());
                throw PanshiException.newError(ErrorCode.DELETE_INSTANCE_FAILED,
                        e.getMessage());
            }
        }
    }
}
