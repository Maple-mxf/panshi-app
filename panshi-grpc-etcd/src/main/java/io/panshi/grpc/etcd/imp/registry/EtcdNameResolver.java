package io.panshi.grpc.etcd.imp.registry;

public final class EtcdNameResolver extends io.grpc.NameResolver {

    public EtcdNameResolver(String etcdMultiAddr) {
    }

    @Override
    public String getServiceAuthority() {
        return null;
    }

    @Override
    public void start(Listener listener) {

    }

    @Override
    public void shutdown() {

    }
}
