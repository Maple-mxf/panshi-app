package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.event.WatchInstanceListener;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;

/**
 * 给予
 */
public interface InstanceRepository extends Repository {

    // etcd存储的根路径
    // ${ROOT_PATH}/${namespace}/${set}/${service} : ${serviceInfo}
    String ROOT_PATH = "/panshi/grpc";

    void watchInstanceChangeStream( WatchInstanceListener listener  );

    /**
     * 新增服务实例
     * @param instance 服务实例信息
     * @return success
     */
    void putInstanceInfo(Instance instance) throws PanshiException;

    List<Instance> getInstanceList(String namespace,String set, String service);
    List<Instance> getInstanceList(  );

    void deleteInstance(Instance instance) throws PanshiException;
}
