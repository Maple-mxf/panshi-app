package io.panshi.grpc.etcd.imp.config;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.config.EtcdConfig;
import io.panshi.grpc.etcd.api.exception.PanshiException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ConfigImp implements Config {

    @Nonnull
    @Override
    public EtcdConfig getEtcdConfig() {
        return null;
    }

    @Override
    public Optional<String> getNamespace() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getService() {
        return Optional.empty();
    }

    @Nullable
    @Override
    public Optional<String> getSet() {
        return Optional.empty();
    }

    @Override
    public void check() throws PanshiException {

    }

    @Nullable
    @Override
    public Optional<Integer> getPort() {
        return Optional.empty();
    }
}
