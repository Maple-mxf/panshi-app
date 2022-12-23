package io.panshi.grpc.discovery;

import io.panshi.discovery.core.api.model.Instance;


public interface RegisterHook {

    void beforeRegister(Instance instance);

    void afterRegister( );

}
