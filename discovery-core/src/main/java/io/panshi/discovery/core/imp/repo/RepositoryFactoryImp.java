package io.panshi.discovery.core.imp.repo;

import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.repo.InstanceRepository;
import io.panshi.discovery.core.api.repo.LockRepository;
import io.panshi.discovery.core.api.repo.NamespaceRepository;
import io.panshi.discovery.core.api.repo.RepositoryFactory;
import io.panshi.discovery.core.api.repo.ServiceRepository;

import javax.annotation.Nonnull;

public class RepositoryFactoryImp  implements RepositoryFactory {

    private final LockRepository lockRepository;
    private final NamespaceRepository namespaceRepository;
    private final ServiceRepository serviceRepository;
    private final InstanceRepository instanceRepository;

    public RepositoryFactoryImp(Config config)  throws PanshiException {
        this.lockRepository = new LockRepositoryImp(config);
        this.namespaceRepository = new NamespaceRepositoryImp(config);
        this.serviceRepository = new ServiceRepositoryImp(config);
        this.instanceRepository = new InstanceRepositoryImp(config);
    }

    @Nonnull
    @Override
    public InstanceRepository getInstanceRepository() {
        return instanceRepository;
    }

    @Nonnull
    @Override
    public NamespaceRepository getNamespaceRepository() {
        return namespaceRepository;
    }

    @Nonnull
    @Override
    public ServiceRepository getServiceRepository() {
        return serviceRepository;
    }

    @Nonnull
    @Override
    public LockRepository getLockRepository() {
        return lockRepository;
    }
}
