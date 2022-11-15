package io.panshi.grpc.discovery;

import io.grpc.ClientInterceptor;
import io.panshi.discovery.core.api.config.Config;

public abstract class  PanshiClientInterceptor implements ClientInterceptor {

    /**
     * client 侧的拦截器，自动注入当前grpc-server与panshi有关的信息
     *
     * @param namespace 当前主调服务所在的命名空间
     * @param applicationName 当前主调服务的应用名称
     * @param config 上下文信息，整个服务调用者进程一个
     */
    public abstract void init(final String namespace, final String applicationName, final Config config);

}