package io.panshi.grpc.etcd.imp.registry;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class EtcdNameResolverProvider extends NameResolverProvider {

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 6;
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
//        return new EtcdNameResolver();
        return null;
    }

    @Override
    public String getDefaultScheme() {
        return "etcd";
    }
}
