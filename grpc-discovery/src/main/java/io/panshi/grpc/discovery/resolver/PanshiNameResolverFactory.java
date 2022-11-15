package io.panshi.grpc.discovery.resolver;

import io.grpc.NameResolverRegistry;
import io.panshi.discovery.core.api.config.Config;

public class PanshiNameResolverFactory {

    private PanshiNameResolverFactory() {}

    public static void init(final Config config) {
        NameResolverRegistry.getDefaultRegistry().register(
                new PanshiNameResolverProvider(config)
        );
    }
}
