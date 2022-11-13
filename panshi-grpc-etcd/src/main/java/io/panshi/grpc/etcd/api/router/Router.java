package io.panshi.grpc.etcd.api.router;

import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;

public interface Router {

    /**
     * 选取符合条件的实例列表
     * @param instanceList
     * @return
     */
    List<Instance> selectInstanceList(List<Instance> instanceList);
}
