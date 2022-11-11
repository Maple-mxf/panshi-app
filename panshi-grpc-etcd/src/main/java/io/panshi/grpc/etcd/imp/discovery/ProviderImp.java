package io.panshi.grpc.etcd.imp.discovery;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.discovery.Provider;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.ServiceInstanceRepository;
import io.panshi.grpc.etcd.imp.repo.ServiceInstanceRepositoryImp;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ProviderImp implements Provider {

    private final ServiceInstanceRepository serviceInstanceRepository = ServiceInstanceRepositoryImp.getInstance();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final String namespace;
    private final String service;
    @Nullable
    private final String set;
    public ProviderImp(Config config){
        this.namespace = config.getNamespace();
        this.service = config.getService();
        this.set = config.getSet().orElse(System.getenv(Config.SET_ENV_VAR));
    }
    @Override
    public boolean registerServiceInstance(Instance instance) {
        return serviceInstanceRepository.addInstanceInfo(instance);
    }

    @Override
    public boolean deregisterServiceInstance(Instance instance) {
        return false;
    }

    @Override
    public boolean reportHeartbeat(Instance instance) {
        return false;
    }

    @Override
    public void close() throws Exception {
        scheduledExecutorService.shutdown();
    }

    @Override
    public void start() {

        // 启动心跳上报任务
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            Instance instance = new Instance();
            instance.setNamespace(namespace);
            instance.setNamespace(service);
            instance.setSet(set);

            reportHeartbeat(instance);
        },0,5, TimeUnit.SECONDS);
    }

}
