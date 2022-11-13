package io.panshi.grpc.etcd.api.config;

import io.panshi.grpc.etcd.api.exception.PanshiException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface Config {

    String SET_ENV_VAR = "biz_set";

    // 获取etcd的连接地址
    @Nonnull
    EtcdConfig getEtcdConfig();

    Optional<String> getNamespace();

    Optional<String> getService();

    @Nullable
    Optional<String> getSet();

    @Nullable
    Optional<Integer> getPort();

    void check() throws PanshiException;

}
