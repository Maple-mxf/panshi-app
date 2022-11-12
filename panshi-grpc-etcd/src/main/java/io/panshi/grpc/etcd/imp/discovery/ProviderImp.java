package io.panshi.grpc.etcd.imp.discovery;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.discovery.Provider;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import io.panshi.grpc.etcd.imp.repo.InstanceRepositoryImp;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProviderImp implements Provider {
    private final InstanceRepository instanceRepository = InstanceRepositoryImp.getInstance();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final String namespace;
    private final String service;
    @Nullable
    private final String set;

    public ProviderImp(Config config) {
        this.namespace = config.getNamespace()
                .orElseThrow(() -> new IllegalArgumentException("namespace require"));
        this.service = config.getService()
                .orElseThrow(() -> new IllegalArgumentException("service require"));
        this.set = config.getSet().orElse(System.getenv(Config.SET_ENV_VAR));
    }

    @Override
    public boolean registerServiceInstance(Instance instance) {
        return instanceRepository.putInstanceInfo(instance);
    }

    @Override
    public boolean deregisterServiceInstance(Instance instance) {
        return false;
    }

    @Override
    public boolean reportHeartbeat(Instance instance) {
        return instanceRepository.updateHeartBeatRecord(instance);
    }

    @Override
    public void close() throws Exception {
        scheduledExecutorService.shutdown();
    }

    @Override
    public void start() {
        // 启动心跳上报任务
        scheduledExecutorService.scheduleAtFixedRate(
                new ReportHeartbeatTask(),
                0, 5, TimeUnit.SECONDS);
    }


    private class ReportHeartbeatTask implements Runnable {
        @Override
        public void run() {
            Instance instance = new Instance();
            instance.setNamespace(namespace);
            instance.setNamespace(service);
            instance.setSet(set);

            try {
                ProviderImp.this.reportHeartbeat(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
