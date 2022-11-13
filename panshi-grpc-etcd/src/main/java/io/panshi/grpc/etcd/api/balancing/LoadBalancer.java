package io.panshi.grpc.etcd.api.balancing;

import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;

public interface LoadBalancer {

    Instance selectOneInstance(List<Instance> instanceList);
}
