package io.panshi.grpc.etcd.imp.discovery;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.discovery.Provider;
import io.panshi.grpc.etcd.api.exception.ErrorCode;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.model.Namespace;
import io.panshi.grpc.etcd.api.model.Service;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import io.panshi.grpc.etcd.api.repo.LockRepository;
import io.panshi.grpc.etcd.api.repo.NamespaceRepository;
import io.panshi.grpc.etcd.api.repo.RepositoryFactory;
import io.panshi.grpc.etcd.api.repo.ServiceRepository;
import io.panshi.grpc.etcd.imp.repo.InstanceRepositoryImp;
import io.panshi.grpc.etcd.imp.repo.RepositoryFactoryImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProviderImp implements Provider {
    private final RepositoryFactory repositoryFactory = new RepositoryFactoryImp(null);
    private final InstanceRepository instanceRepository = repositoryFactory.getInstanceRepository();
    private final NamespaceRepository namespaceRepository = repositoryFactory.getNamespaceRepository();
    private final ServiceRepository serviceRepository = repositoryFactory.getServiceRepository();
    private final LockRepository lockRepository = repositoryFactory.getLockRepository();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRepositoryImp.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final String namespace;
    private final String service;
    // default value  = "default"
    private final String set;
    private final Instance instance;

    public ProviderImp(Config config) throws PanshiException {
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

        // 1 check namespace
        Namespace ns = new Namespace();
        ns.setName(instance.getNamespace());
        ns.setCreateTime(LocalDateTime.now());
        try {
            boolean exist = namespaceRepository.exist(instance.getNamespace());
            if (!exist){
                // 1 lock
                String lockKey = lockRepository.lock(String.format("create_ns_%s", instance.getNamespace()), 5);
                // 2 create namespace
                namespaceRepository.putNamespace(ns);
                // 3 unlock
                lockRepository.unLock(lockKey,5);
            }
        } catch (PanshiException e) {
            e.printStackTrace();
            LOGGER.error("");
            try {
                boolean exist = namespaceRepository.exist(instance.getNamespace());
                if (!exist) throw PanshiException.newError(ErrorCode.UNKNOWN_ERROR,"namespace create error");
            } catch (PanshiException ex) {
                throw new IllegalStateException(ex.formatMessage());
            }
        }

        // 2 check service
        Service srv = new Service();
        srv.setNamespace(ns.getName());
        srv.setName(service);
        srv.setSet(set);
        srv.setCreateTime(LocalDateTime.now());
        try {
            boolean exist = serviceRepository.exist(srv);
            if (!exist){
                // 1 lock
                String lockKey = lockRepository.lock(String.format("create_service_%s_%s_%s",
                        srv.getNamespace(),srv.getName(),srv.getSet()), 5);
                // 2 create namespace
                serviceRepository.putService(srv);
                // 3 unlock
                lockRepository.unLock(lockKey,5);
            }
        } catch (PanshiException e) {
            e.printStackTrace();
            LOGGER.error("");
            try {
                boolean exist = serviceRepository.exist(srv);
                if (!exist) throw PanshiException.newError(ErrorCode.UNKNOWN_ERROR,"namespace create error");
            } catch (PanshiException ex) {
                throw new IllegalStateException(ex.formatMessage());
            }
        }

        // 3 put instance info
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
