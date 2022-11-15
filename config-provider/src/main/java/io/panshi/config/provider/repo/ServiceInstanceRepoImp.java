package io.panshi.config.provider.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.panshi.config.provider.model.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.panshi.config.provider.config.RepoClient.ETCD_CLIENT;

public class ServiceInstanceRepoImp {
    private ServiceInstanceRepoImp(){}

    private final static String ROOT_PREFIX = "/panshi/serviceInstance";
    private final static ObjectMapper jsonMapper = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceInstanceRepoImp.class);

    public static final ServiceInstanceRepoImp instance =
            new ServiceInstanceRepoImp();

    public boolean putServiceInstance(ServiceInstance serviceInstance){
        return true;
    }

    public boolean deleteServiceInstance(String group ,
                                         String set, String ipAddress){
        return true;
    }

    public List<ServiceInstance> getServiceInstanceList(){
        ByteSequence prefixKey = ByteSequence.from(
                ROOT_PREFIX, StandardCharsets.UTF_8);
        GetOption option = GetOption.newBuilder().isPrefix(true)
                .withSortField(GetOption.SortTarget.KEY)
                .build();
        try{
            CompletableFuture<GetResponse> future = ETCD_CLIENT.getKVClient().get(prefixKey, option);
            GetResponse response = future.get(3, TimeUnit.SECONDS);
            List<KeyValue> kvs = response.getKvs();
           return kvs.stream()
                    .map(kv->{
                        try {
                            return jsonMapper.readValue(kv.getValue().getBytes(),
                                    ServiceInstance.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                                }
                            }
                    )
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
