package io.panshi.discovery.core.api.repo;

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
