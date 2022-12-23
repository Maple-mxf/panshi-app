package io.panshi.discovery.core.api.discovery;

import io.panshi.discovery.core.api.model.ServiceDefinition;

public interface Provider extends DiscoveryClient {

    boolean registerInstance(RegisterInstanceRequest request);

    boolean deregisterInstance(ServiceDefinition serviceDefinition);
}
