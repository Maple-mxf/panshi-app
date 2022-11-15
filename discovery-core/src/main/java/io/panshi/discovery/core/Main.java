package io.panshi.discovery.core;

import io.grpc.NameResolverRegistry;
import io.panshi.discovery.core.imp.registry.EtcdNameResolverProvider;

/**
 * @see io.grpc.NameResolver
 * @see io.grpc.NameResolver
 */
public class Main {

    public static void main(String[] args){
        NameResolverRegistry.getDefaultRegistry()
                .register(
                        new EtcdNameResolverProvider()
                );
    }
}
