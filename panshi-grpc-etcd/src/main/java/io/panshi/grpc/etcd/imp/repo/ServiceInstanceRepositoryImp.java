package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;
import io.panshi.grpc.etcd.api.event.WatchInstanceEvent;
import io.panshi.grpc.etcd.api.event.WatchInstanceListener;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.ServiceInstanceRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class ServiceInstanceRepositoryImp implements ServiceInstanceRepository {
    private ServiceInstanceRepositoryImp(){}

    public static ServiceInstanceRepositoryImp getInstance(){
        return new ServiceInstanceRepositoryImp();
    }

    private final Client client = null; // TODO

    @Override
    public boolean addInstanceInfo(Instance instance) {
        return false;
    }

    @Override
    public boolean deleteInstanceInfo(Instance instance) {
        return false;
    }

    @Override
    public boolean updateHeartBeatRecord(Instance instance) {
        return false;
    }

    @Override
    public List<Instance> getInstanceList(String namespace, String set, String service) {
        return null;
    }

    @Override
    public boolean getLock(String lockKey, int leaseTime) {
        return false;
    }

    @Override
    public boolean releaseLock(String lockKey) {
        return false;
    }

    @Override
    public void watchInstanceChangeStream( WatchInstanceListener listener  ) {

        ByteSequence key = ByteSequence.from(ServiceInstanceRepository.ROOT_PATH,
                StandardCharsets.UTF_8);
        WatchOption option = WatchOption.newBuilder().isPrefix(true).build();

        client.getWatchClient()
                .watch(
                        key, option,
                        new Consumer<WatchResponse>() {
                            @Override
                            public void accept(WatchResponse response) {
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

                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {

                            }
                        }
                );

    }
}
