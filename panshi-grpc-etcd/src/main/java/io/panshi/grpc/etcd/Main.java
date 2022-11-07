package io.panshi.grpc.etcd;

import io.grpc.NameResolverRegistry;

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
