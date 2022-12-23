package io.panshi.discovery.core.imp.discovery;

import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.discovery.Provider;
import io.panshi.discovery.core.api.discovery.RegisterInstanceRequest;
import io.panshi.discovery.core.api.exception.ErrorCode;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Instance;
import io.panshi.discovery.core.api.model.Namespace;
import io.panshi.discovery.core.api.model.Service;
import io.panshi.discovery.core.api.model.ServiceDefinition;
import io.panshi.discovery.core.api.repo.InstanceRepository;
import io.panshi.discovery.core.api.repo.LockRepository;
import io.panshi.discovery.core.api.repo.NamespaceRepository;
import io.panshi.discovery.core.api.repo.RepositoryFactory;
import io.panshi.discovery.core.api.repo.ServiceRepository;
import io.panshi.discovery.core.imp.repo.InstanceRepositoryImp;
import io.panshi.discovery.core.imp.repo.RepositoryFactoryImp;
import io.panshi.discovery.core.imp.util.NetworkHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


// TODO provider api不应该具有诸多状态属性
public class ProviderImp implements Provider {
    private final InstanceRepository instanceRepository ;
    private final NamespaceRepository namespaceRepository  ;
    private final ServiceRepository serviceRepository ;
    private final LockRepository lockRepository ;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceRepositoryImp.class);

    public ProviderImp(Config config) throws PanshiException {

        RepositoryFactory repositoryFactory = new RepositoryFactoryImp(config);
        this.instanceRepository = repositoryFactory.getInstanceRepository();
        this.namespaceRepository = repositoryFactory.getNamespaceRepository();
        this.serviceRepository = repositoryFactory.getServiceRepository();
        this.lockRepository = repositoryFactory.getLockRepository();
    }

    @Override
    public boolean registerInstance(RegisterInstanceRequest registerInstanceRequest) {



        // 1 check namespace
        Namespace ns = new Namespace();
        ns.setName(registerInstanceRequest.getNamespace());
        ns.setCreateTime(LocalDateTime.now());
        try {
            boolean exist = namespaceRepository.exist(registerInstanceRequest.getNamespace());
            if (!exist){
                // 1 lock
                String lockKey = lockRepository.lock(String.format("create_ns_%s", registerInstanceRequest.getNamespace()), 5);
                // 2 create namespace
                namespaceRepository.putNamespace(ns);
                // 3 unlock
                lockRepository.unLock(lockKey,5);
            }
        } catch (PanshiException e) {
            e.printStackTrace();
            LOGGER.error("");
            try {
                boolean exist = namespaceRepository.exist(registerInstanceRequest.getNamespace());
                if (!exist) throw PanshiException.newError(ErrorCode.UNKNOWN_ERROR,"namespace create error");
            } catch (PanshiException ex) {
                throw new IllegalStateException(ex.formatMessage());
            }
        }

        // 2 check service
        Service srv = new Service();
        srv.setNamespace(ns.getName());
        srv.setName(registerInstanceRequest.getService());
        srv.setApplication(registerInstanceRequest.getApplication());
        srv.setSet(registerInstanceRequest.getSet());
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

        // 3 register info
        Instance instance = new Instance();
        instance.setNamespace(registerInstanceRequest.getNamespace());
        instance.setService(registerInstanceRequest.getService());
        instance.setSet(registerInstanceRequest.getSet());
        instance.setHost(NetworkHelper.getLocalHost());
        instance.setPort(registerInstanceRequest.getPort());
        instance.setVersion(registerInstanceRequest.getVersion());
        instance.setWeight(registerInstanceRequest.getWeight()); // TODO 权重设置

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
    public boolean deregisterInstance(ServiceDefinition serviceDefinition) {
        instanceRepository.stop();
        return true;
    }


    @Override
    public void close() throws Exception {
        instanceRepository.stop();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        this.instanceRepository.stop();
        this.lockRepository.stop();
        this.namespaceRepository.stop();
        this.serviceRepository.stop();
    }
}
