package io.panshi.grpc.etcd.api.repo;

import io.etcd.jetcd.Client;

import javax.annotation.Nonnull;

public interface Repository {

    @Nonnull
    Client getRepoClient();

    /**
     *
     * @param lockKey
     * @param leaseTime
     * @return
     */
    boolean tryLock(String lockKey,int leaseTime);

    /**
     *
     * @param lockKey
     * @return
     */
    boolean releaseLock(String lockKey);
}
