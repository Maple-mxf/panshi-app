package io.panshi.grpc.etcd.api.router;

import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;

public interface Router {

    List<Instance> selectInstances();
}
