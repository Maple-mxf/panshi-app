package io.panshi.grpc.etcd;

import io.grpc.NameResolverRegistry;
import io.panshi.grpc.etcd.imp.registry.EtcdNameResolverProvider;

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
