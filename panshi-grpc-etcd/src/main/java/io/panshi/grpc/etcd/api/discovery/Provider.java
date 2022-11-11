package io.panshi.grpc.etcd.api.discovery;

import io.panshi.grpc.etcd.api.model.Instance;

public interface Provider extends DiscoveryClient {

    boolean registerServiceInstance(Instance instance);

    boolean deregisterServiceInstance(Instance instance);

    boolean reportHeartbeat(Instance instance);
}
