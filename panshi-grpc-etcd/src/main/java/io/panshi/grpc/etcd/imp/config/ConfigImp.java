package io.panshi.grpc.etcd.imp.config;

import io.panshi.grpc.etcd.api.config.Config;

import javax.annotation.Nullable;
import java.util.Optional;

public class ConfigImp implements Config {

    @Override
    public String[] getEtcdConnectionStrings() {
        return new String[0];
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
}
