package io.panshi.grpc.etcd.imp.discovery;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.discovery.Provider;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import io.panshi.grpc.etcd.imp.repo.InstanceRepositoryImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProviderImp implements Provider {
    private final InstanceRepository instanceRepository = InstanceRepositoryImp.getInstance();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRepositoryImp.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final String namespace;
    private final String service;
    // default value  = "default"
    private final String set;
    private final Instance instance;

    public ProviderImp(Config config) {
        this.namespace = config.getNamespace()
                .orElseThrow(() -> new IllegalArgumentException("namespace require"));
        this.service = config.getService()
                .orElseThrow(() -> new IllegalArgumentException("service require"));
        this.set = config.getSet().orElse(System.getenv(Config.SET_ENV_VAR));

        this.instance = new Instance();
        this.instance.setNamespace(namespace);
        this.instance.setSet(set);
        this.instance.setService(service);
//        this.instance.setIp(config.get);
        this.instance.setPort(config.getPort().get());
    }

    @Override
    public boolean registerServiceInstance() {
        if (this.running.get()){
            LOGGER.warn("provider already running");
            return false;
        }
        try{
            instanceRepository.putInstanceInfo(instance);
            return true;
        }catch (PanshiException e){
            LOGGER.error("put instance exception code = {} message = {} instance = {} ",
                    e.getErrorCode(), e.getMessage(), instance);
            throw new IllegalStateException(
                    String.format("provider register instance info failed, message = %s",
                            e.getMessage()));
        }
    }

    @Override
    public boolean deregisterServiceInstance() {
        instanceRepository.stop();
        return true;
    }

    @Override
    public void close() throws Exception {
        instanceRepository.stop();
    }

    @Override
    public void start() {
        // 注册服务实例
        if (registerServiceInstance() && this.running.compareAndSet(false,true)){
            LOGGER.info("provider init success, namespace {} service {} set {} ",
                    namespace,service,set);
        }
    }

}
