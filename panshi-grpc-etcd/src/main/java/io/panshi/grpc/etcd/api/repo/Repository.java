package io.panshi.grpc.etcd.api.repo;

import io.etcd.jetcd.Client;

import javax.annotation.Nonnull;


/**
 * @see io.etcd.jetcd.common.exception.EtcdException
 * @see io.etcd.jetcd.common.exception.ErrorCode
 */
public interface Repository {

    @Nonnull
    Client getRepoClient();

    /**
     * @param lockKey
     * @param leaseTime
     * @return
     */
    boolean lock(String lockKey, int leaseTime);

    /**
     * @param lockKey
     * @return
     */
    boolean unLock(String lockKey);

    void stop();
}
