package io.panshi.discovery.core.api.config;

import io.panshi.discovery.core.api.exception.PanshiException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Config {

    // 获取etcd的连接地址
    @Nonnull
    EtcdConfig getEtcdConfig();

    String getNamespace();

    String getApplicationName();

    @Nullable
    String getSet();

    void check() throws PanshiException;

}
