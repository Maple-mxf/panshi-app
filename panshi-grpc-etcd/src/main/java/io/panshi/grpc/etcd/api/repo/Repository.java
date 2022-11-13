package io.panshi.grpc.etcd.api.repo;

import io.etcd.jetcd.Client;
import io.panshi.grpc.etcd.api.exception.PanshiException;

import javax.annotation.Nonnull;


/**
 * @see io.etcd.jetcd.common.exception.EtcdException
 * @see io.etcd.jetcd.common.exception.ErrorCode
 */
public interface Repository {

    @Nonnull
    Client getRepoClient();

    long getLeaseId();

    /**
     * @param lockKey
     * @param leaseTime
     * @return
     */
    String lock(String lockKey, int leaseTime) throws PanshiException;

    /**
     * @param lockKey
     * @return
     */
    boolean unLock(String lockKey);

    void stop();
}
