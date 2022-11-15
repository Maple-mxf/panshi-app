package io.panshi.grpc.etcd.api.repo;

import io.etcd.jetcd.Client;
import io.panshi.grpc.etcd.api.exception.PanshiException;

import javax.annotation.Nonnull;


/**
 * @see io.etcd.jetcd.common.exception.EtcdException
 * @see io.etcd.jetcd.common.exception.ErrorCode
 */
public interface Repository {

    // etcd存储的根路径
    // ${ROOT_PATH}/${namespace}/${set}/${service} : ${serviceInfo}
    String ROOT_PATH = "/panshi/grpc";

    @Nonnull
    Client getRepoClient();

    /**
     *
     * @return lease id
     */
    long getClientGlobalLeaseId();

    void stop();
}
