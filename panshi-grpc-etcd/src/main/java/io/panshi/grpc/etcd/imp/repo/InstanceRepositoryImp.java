package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.event.WatchInstanceEvent;
import io.panshi.grpc.etcd.api.event.WatchInstanceListener;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class InstanceRepositoryImp extends AbstractInstanceRepository implements InstanceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRepositoryImp.class);

    protected InstanceRepositoryImp(Config config) {
        super(config);
    }

    public static InstanceRepositoryImp getInstance(){
        return new InstanceRepositoryImp(null);
    }

    @Override
    public boolean putInstanceInfo(Instance instance) {
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
    public List<Instance> getInstanceList() {
        return null;
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
                        }
                );

    }
}
