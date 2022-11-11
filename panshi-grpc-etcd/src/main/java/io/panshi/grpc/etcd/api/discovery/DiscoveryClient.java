package io.panshi.grpc.etcd.api.discovery;

public interface DiscoveryClient extends AutoCloseable {

    /**
     * 启动
     */
    void start();
}
