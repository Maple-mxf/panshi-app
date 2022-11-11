package io.panshi.grpc.etcd.imp.discovery;

import io.panshi.grpc.etcd.api.discovery.Consumer;
import io.panshi.grpc.etcd.api.event.WatchInstanceEvent;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.model.RpcInvokeResult;
import io.panshi.grpc.etcd.api.model.ServiceInfo;
import io.panshi.grpc.etcd.api.model.ServiceKey;
import io.panshi.grpc.etcd.api.repo.ServiceInstanceRepository;
import io.panshi.grpc.etcd.imp.repo.ServiceInstanceRepositoryImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConsumerImp implements Consumer {

    private final ServiceInstanceRepository serviceInstanceRepository = ServiceInstanceRepositoryImp.getInstance();
    private final ConcurrentHashMap<ServiceKey,List<Instance>> instanceCache = new ConcurrentHashMap<>();

    @Override
    public List<Instance> listHealthInstances(ServiceKey key) {
        return instanceCache.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public boolean collectInvokeMetric(RpcInvokeResult invokeResult) {
        return false;
    }

    @Override
    public void start() {
        initCache();
        watch();
    }

    @Override
    public void close() throws Exception {
    }

    private void initCache(){
        List<Instance> instanceList = serviceInstanceRepository.getInstanceList();

        for (Instance instance : instanceList) {
//            this.instanceCache.put()
        }
    }

    // 监听服务Instance的注册
    private void watch(){

        // 定时加载Instance
        serviceInstanceRepository.watchInstanceChangeStream(
                event -> {

                    if ( WatchInstanceEvent.Type.REGISTER.equals(event.getType()) )
                    {
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
                        if (!present){
                            instanceList.add(event.getInstance());
                        }

                        instanceCache.put(serviceKey, instanceList);
                    }
                }
        );

    }
}
