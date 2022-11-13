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

    /**
     *
     * @return lease id
     */
    long getClientGlobalLeaseId();

    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_concurrency_reference_v3/
     * @param identifierName 锁的唯一名称
     * @param waitSeconds 等待的时长
     * @return key is a key that will exist on etcd for the duration
     * that the Lock caller owns the lock. Users should
     * not modify this key or the lock may exhibit undefined behavior.
     */
    String lock(String identifierName,int waitSeconds) throws PanshiException;

    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_concurrency_reference_v3/
     *
     * @param lockKey {@link Repository#lock(String, int)} return lock key
     * @param waitSeconds 等待的时长
     */
    void unLock(String lockKey, int waitSeconds) throws PanshiException;

    void stop();
}
