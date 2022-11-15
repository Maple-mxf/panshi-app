package io.panshi.grpc.etcd.api.repo;

import javax.annotation.Nonnull;

public interface RepositoryFactory {

    @Nonnull
    InstanceRepository getInstanceRepository()  ;

    @Nonnull
    NamespaceRepository getNamespaceRepository() ;

    @Nonnull
    ServiceRepository getServiceRepository() ;

    @Nonnull
    LockRepository getLockRepository() ;
}
