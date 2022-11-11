package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.event.WatchInstanceListener;
import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;

public interface ServiceInstanceRepository {

    // etcd存储的根路径
    // ${ROOT_PATH}/${namespace}/${set}/${service} : ${serviceInfo}
    String ROOT_PATH = "/panshi/grpc";

    void watchInstanceChangeStream( WatchInstanceListener listener  );

    /**
     * 新增服务实例
     * @param instance 服务实例信息
     * @return success
     */
    boolean addInstanceInfo(Instance instance);

    /**
     * 删除服务实例
     * @param instance 服务实例信息
     * @return success
     */
    boolean deleteInstanceInfo(Instance instance);

    /**
     * 更新服务实例的心跳时间
     * @param instance 服务实例信息
     * @return success
     */
    boolean updateHeartBeatRecord(Instance instance);

    /**
     *
     * @param lockKey
     * @param leaseTime
     * @return
     */
    boolean getLock(String lockKey,int leaseTime);

    /**
     *
     * @param lockKey
     * @return
     */
    boolean releaseLock(String lockKey);


    List<Instance> getInstanceList(String namespace,String set, String service);
    List<Instance> getInstanceList( );
}
