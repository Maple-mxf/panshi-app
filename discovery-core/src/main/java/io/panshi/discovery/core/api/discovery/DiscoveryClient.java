package io.panshi.discovery.core.api.discovery;

public interface DiscoveryClient extends AutoCloseable {

    /**
     * 启动
     */
    void start();

    void stop();
}
