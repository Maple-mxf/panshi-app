package io.panshi.discovery.core.imp.discovery;

import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.discovery.Consumer;
import io.panshi.discovery.core.api.event.WatchServiceEvent;
import io.panshi.discovery.core.api.event.WatchServiceListener;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Instance;
import io.panshi.discovery.core.api.model.RpcInvokeResult;
import io.panshi.discovery.core.api.model.ServiceInfo;
import io.panshi.discovery.core.api.model.ServiceKey;
import io.panshi.discovery.core.api.repo.InstanceRepository;
import io.panshi.discovery.core.api.repo.RepositoryFactory;
import io.panshi.discovery.core.imp.balancing.WeightRandomLoadBalancer;
import io.panshi.discovery.core.imp.repo.RepositoryFactoryImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConsumerImp implements Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerImp.class);

    private final RepositoryFactory repositoryFactory ;
    private final InstanceRepository instanceRepository ;

    private final WeightRandomLoadBalancer loadBalancer = new WeightRandomLoadBalancer();
    private final WatchServiceListener  defaultServiceChangeListener = new ServiceChangeStreamListener();

    private final ConcurrentHashMap<ServiceKey, List<Instance>> instanceCache = new ConcurrentHashMap<>();
//    private final EventBus eventBus = new AsyncEventBus(
//            Executors.newSingleThreadExecutor(),
//            (exception, context) -> LOGGER.error("received event bus error {} event {} ",
//                    exception.getMessage(),context.getEvent()));
    private final Config config;

    public ConsumerImp(Config config) throws PanshiException {
        this.config = config;
        config.check();

        this.repositoryFactory = new RepositoryFactoryImp(config);
        this.instanceRepository = repositoryFactory.getInstanceRepository();
    }

    @Override
    public List<Instance> listHealthInstances(ServiceKey key) {
        return instanceCache.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(t->t.getWeight()>0L)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public Instance getHealthInstance(ServiceKey key) {
        List<Instance> instances = listHealthInstances(key);
        return loadBalancer.selectOneInstance(instances);
    }

    @Override
    public boolean collectInvokeMetric(RpcInvokeResult invokeResult) {
        return false;
    }

    @Override
    public void start() {
        initCache();
        registerListener(defaultServiceChangeListener);
        LOGGER.info("consumer started");
    }

    @Override
    public void close() throws Exception {
        this.instanceRepository.stop();
    }

    private void initCache() {
        List<Instance> instanceList = instanceRepository.getInstanceList();
        Map<ServiceKey, List<Instance>> instanceMap = instanceList.stream().collect(Collectors.groupingBy(instance -> {
            ServiceKey serviceKey = new ServiceKey();
            serviceKey.setNamespace(instance.getNamespace());
            serviceKey.setService(instance.getService());
            serviceKey.setSet(instance.getSet());
            return serviceKey;
        }));
        this.instanceCache.putAll(instanceMap);
    }


    @Override
    public void registerListener(WatchServiceListener listener) {
        instanceRepository.watchInstanceChangeEvent(listener);
        LOGGER.info("");
    }

     class ServiceChangeStreamListener implements WatchServiceListener {
        @Override
        public void handle(WatchServiceEvent event) {
            if (WatchServiceEvent.Type.REGISTER.equals(event.getType())) {
                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setNamespace(event.getInstance().getNamespace());
                serviceInfo.setService(event.getInstance().getService());
                serviceInfo.setSet(event.getInstance().getSet());

                ServiceKey serviceKey = new ServiceKey();
                serviceKey.setNamespace(event.getInstance().getNamespace());
                serviceKey.setService(event.getInstance().getService());
                serviceKey.setSet(event.getInstance().getSet());

                List<Instance> instanceList = instanceCache.getOrDefault(serviceKey, new ArrayList<>());
                boolean present = instanceList.stream().anyMatch(t -> t.equals(event.getInstance()));
                if (!present) {
                    instanceList.add(event.getInstance());
                }
                instanceCache.put(serviceKey, instanceList);
            }
        }
    }

    @Override
    public void stop() {
        this.instanceRepository.stop();
    }
}
