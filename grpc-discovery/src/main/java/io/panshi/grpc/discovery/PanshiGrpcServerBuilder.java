package io.panshi.grpc.discovery;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.BindableService;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.HandlerRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerServiceDefinition;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.imp.config.ConfigImp;

import javax.annotation.Nullable;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class PanshiGrpcServerBuilder extends ServerBuilder<PanshiGrpcServerBuilder> {
    private static final String DEFAULT_NAMESPACE = "default";

    private static final int DEFAULT_TTL = 5;

    private String applicationName;

    private String namespace;

    private Map<String, String> metaData = new HashMap<>();

    private int weight;

    private String version;

    private int heartbeatInterval;

    private String host;

    private DelayRegister delayRegister;

    private RegisterHook registerHook;

    /**
     * gRPC-Server 优雅关闭最大等待时长
     */
    private Duration maxWaitDuration = Duration.ofSeconds(30);

    private boolean openGraceOffline = true;

    private final ServerBuilder<?> builder;

    private final List<PanshiServerInterceptor> polarisInterceptors = new ArrayList<>();

    private final List<ServerInterceptor> interceptors = new ArrayList<>();

    private final Config context = ConfigImp.init();

    /**
     * Static factory for creating a new PolarisGrpcServerBuilder.
     *
     * @param port the port to listen on
     * @return PolarisGrpcServerBuilder
     */
    public static PanshiGrpcServerBuilder forPort(int port) {
        ServerBuilder<?> builder = ServerBuilder.forPort(port);
        return new PanshiGrpcServerBuilder(builder);
    }

    /**
     * PolarisGrpcServerBuilder Constructor.
     *
     * @param builder ServerBuilder
     */
    public PanshiGrpcServerBuilder(ServerBuilder<?> builder) {
        this.builder = builder;
    }

    /**
     * Set grpc service name.
     *
     * @param applicationName grpc server name
     * @return PolarisGrpcServerBuilder
     */
    public PanshiGrpcServerBuilder applicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    /**
     * Namespace registered by grpc service.
     *
     * @param namespace polaris namespace
     * @return PolarisGrpcServerBuilder
     */
    public PanshiGrpcServerBuilder namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Set metadata.
     *
     * @param metadata metadata
     * @return PolarisGrpcServerBuilder
     */
    public PanshiGrpcServerBuilder metadata(Map<String, String> metadata) {
        this.metaData = metadata;
        return this;
    }

    /**
     * set instance weight
     *
     * @param weight
     * @return
     */
    public PanshiGrpcServerBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    public PanshiGrpcServerBuilder version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Set the heartbeat report time by default 5 seconds.
     *
     * @param heartbeatInterval Time in seconds
     * @return PolarisGrpcServerBuilder
     */
    public PanshiGrpcServerBuilder heartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
        return this;
    }

    /**
     * Set the local host.
     *
     * @param host host
     * @return PolarisGrpcServerBuilder
     */
    public PanshiGrpcServerBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    @Override
    public PanshiGrpcServerBuilder executor(@Nullable Executor executor) {
        this.builder.executor(executor);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder addService(ServerServiceDefinition service) {
        this.builder.addService(service);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder addService(BindableService bindableService) {
        this.builder.addService(bindableService);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder fallbackHandlerRegistry(@Nullable HandlerRegistry fallbackRegistry) {
        this.builder.fallbackHandlerRegistry(fallbackRegistry);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder useTransportSecurity(File certChain, File privateKey) {
        this.builder.useTransportSecurity(certChain, privateKey);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder decompressorRegistry(@Nullable DecompressorRegistry registry) {
        this.builder.decompressorRegistry(registry);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder compressorRegistry(@Nullable CompressorRegistry registry) {
        this.builder.compressorRegistry(registry);
        return this;
    }

    @Override
    public PanshiGrpcServerBuilder intercept(ServerInterceptor interceptor) {
        if (interceptor instanceof PanshiServerInterceptor) {
            this.polarisInterceptors.add((PanshiServerInterceptor) interceptor);
        } else {
            this.interceptors.add(interceptor);
        }
        return this;
    }

    /**
     * 延迟注册, 用户可以通过设置 {@link DelayRegister} 来延迟 gRPC-server 注册到 polaris 对外提供服务的时间
     * 默认支持策略
     * - {@link  WaitDelayRegister} 等待一段时间在进行注册
     *
     * @param delayRegister {@link DelayRegister}
     * @return {@link PanshiGrpcServerBuilder}
     */
    public PanshiGrpcServerBuilder delayRegister(DelayRegister delayRegister) {
        this.delayRegister = delayRegister;
        return this;
    }

    /**
     * 优雅下线的最大等待时间，如果到了一定时间还没有结束，则直接强制关闭，默认 Duration.ofSeconds(30)
     *
     * @param maxWaitDuration {@link Duration}
     * @return {@link PanshiGrpcServerBuilder}
     */
    public PanshiGrpcServerBuilder maxWaitDuration(Duration maxWaitDuration) {
        this.maxWaitDuration = maxWaitDuration;
        return this;
    }

    public PanshiGrpcServerBuilder openGraceOffline(boolean openGraceOffline) {
        this.openGraceOffline = openGraceOffline;
        return this;
    }

    public PanshiGrpcServerBuilder registerHook(RegisterHook registerHook) {
        this.registerHook = registerHook;
        return this;
    }

    RegisterHook getRegisterHook() {
        return registerHook;
    }

    @Override
    public Server build() {
        setDefault();

        if (openGraceOffline) {
            // 注册统计 server 当前正在处理的请求数量
            this.builder.intercept(GraceOffline.createInterceptor());
        }

        for (PanshiServerInterceptor interceptor : polarisInterceptors) {
            interceptor.init(namespace, applicationName, context);
            this.builder.intercept(interceptor);
        }
        for (ServerInterceptor interceptor : interceptors) {
            this.builder.intercept(interceptor);
        }

        PanshiGrpcServer server;
        try {
            server = new PanshiGrpcServer(this, context, this.builder.build());
        } catch (PanshiException e) {
            throw new RuntimeException(e);
        }
        server.setDelayRegister(delayRegister);

        if (openGraceOffline) {
            server.setMaxWaitDuration(maxWaitDuration);
        }

        return server;
    }

    private void setDefault() {
        if ( Strings.isNullOrEmpty(namespace)) {
            this.namespace = DEFAULT_NAMESPACE;
        }
        if (heartbeatInterval == 0) {
            this.heartbeatInterval = DEFAULT_TTL;
        }
    }

    String getApplicationName() {
        return applicationName;
    }

    String getNamespace() {
        return namespace;
    }

    Map<String, String> getMetaData() {
        return metaData;
    }

    int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    String getHost() {
        return host;
    }

    Config getContext() {
        return context;
    }

    boolean isOpenGraceOffline() {
        return openGraceOffline;
    }

    int getWeight() {
        return weight;
    }

    String getVersion() {
        return version;
    }
}
