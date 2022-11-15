package io.panshi.discovery.core.imp.config;

import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.config.EtcdConfig;
import io.panshi.discovery.core.api.exception.PanshiException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ConfigImp implements Config {

    public static Config init(){
        return null;
    }

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
