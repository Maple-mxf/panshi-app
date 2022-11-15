package io.panshi.discovery.core.api.discovery;

public interface Provider extends DiscoveryClient {

    boolean registerServiceInstance();

    boolean deregisterServiceInstance();
}
