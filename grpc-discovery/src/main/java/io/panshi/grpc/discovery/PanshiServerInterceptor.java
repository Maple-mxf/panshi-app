package io.panshi.grpc.discovery;

import io.grpc.ServerInterceptor;
import io.panshi.discovery.core.api.config.Config;

public abstract class  PanshiServerInterceptor implements ServerInterceptor {
    public abstract void init(final String namespace, final String applicationName, final Config config);
}
