package io.panshi.grpc.etcd.api.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface Config {

    String SET_ENV_VAR = "biz_set";

    // 获取etcd的连接地址
    @Nonnull
    String[] getEtcdConnectionStrings();

    @Nonnull
    String getNamespace();

    @Nonnull
    String getService();

    @Nullable
    Optional<String> getSet();

}
