package io.panshi.grpc.etcd.api.discovery;

public interface Provider extends DiscoveryClient {

    boolean registerServiceInstance();

    boolean deregisterServiceInstance();
}
