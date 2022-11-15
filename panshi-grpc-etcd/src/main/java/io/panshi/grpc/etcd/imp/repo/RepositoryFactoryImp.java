package io.panshi.grpc.etcd.imp.repo;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.repo.InstanceRepository;
import io.panshi.grpc.etcd.api.repo.LockRepository;
import io.panshi.grpc.etcd.api.repo.NamespaceRepository;
import io.panshi.grpc.etcd.api.repo.RepositoryFactory;
import io.panshi.grpc.etcd.api.repo.ServiceRepository;

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
