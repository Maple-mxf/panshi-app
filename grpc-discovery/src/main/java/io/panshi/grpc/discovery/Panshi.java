package io.panshi.grpc.discovery;

import io.grpc.BinaryLog;
import io.grpc.ClientInterceptor;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import io.grpc.ProxyDetector;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.model.ServiceInfo;
import io.panshi.discovery.core.imp.config.ConfigImp;
import io.panshi.discovery.core.imp.util.JsonUtils;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static io.panshi.grpc.discovery.GrpcDiscoveryConstants.LOADBALANCER_PROVIDER;

public class Panshi {

    private static final Config context = ConfigImp.init();

    static {
        PanshiNameResolverFactory.init(context);
    }

    private final ManagedChannelBuilder<?> builder;

    private final List<PanshiClientInterceptor> polarisInterceptors = new ArrayList<>();

    private final List<ClientInterceptor> interceptors = new ArrayList<>();

    private final ServiceInfo sourceService;

    private Panshi(String target, ServiceInfo sourceService) {
        this.builder = ManagedChannelBuilder.forTarget(buildUrl(target, sourceService));
        this.sourceService = sourceService;
    }

    public static Panshi forTarget(String target) {
        return new Panshi(target, null);
    }

    public static Panshi forTarget(String target, ServiceInfo sourceService) {
        return new Panshi(target, sourceService);
    }

    public Panshi directExecutor() {
        builder.directExecutor();
        return this;
    }

    public Panshi executor(Executor executor) {
        builder.executor(executor);
        return this;
    }

    public Panshi intercept(List<ClientInterceptor> interceptors) {

        for (ClientInterceptor interceptor : interceptors) {
            if (interceptor instanceof PanshiClientInterceptor) {
                this.polarisInterceptors.add((PanshiClientInterceptor) interceptor);
            } else {
                this.interceptors.add(interceptor);
            }
        }
        return this;
    }

    public Panshi intercept(ClientInterceptor... interceptors) {
        for (ClientInterceptor interceptor : interceptors) {
            if (interceptor instanceof PanshiClientInterceptor) {
                this.polarisInterceptors.add((PanshiClientInterceptor) interceptor);
            } else {
                this.interceptors.add(interceptor);
            }
        }
        return this;
    }


    public Panshi userAgent(String userAgent) {
        builder.userAgent(userAgent);
        return this;
    }


    public Panshi overrideAuthority(String authority) {
        builder.overrideAuthority(authority);
        return this;
    }


    @Deprecated
    public Panshi nameResolverFactory(NameResolver.Factory resolverFactory) {
        builder.nameResolverFactory(resolverFactory);
        return this;
    }


    public Panshi decompressorRegistry(DecompressorRegistry registry) {
        builder.decompressorRegistry(registry);
        return this;
    }


    public Panshi compressorRegistry(CompressorRegistry registry) {
        builder.compressorRegistry(registry);
        return this;
    }


    public Panshi idleTimeout(long value, TimeUnit unit) {
        builder.idleTimeout(value, unit);
        return this;
    }


    public Panshi offloadExecutor(Executor executor) {
        this.builder.offloadExecutor(executor);
        return this;
    }


    public Panshi usePlaintext() {
        this.builder.usePlaintext();
        return this;
    }


    public Panshi useTransportSecurity() {
        this.builder.useTransportSecurity();
        return this;
    }

    public Panshi enableFullStreamDecompression() {
        this.builder.enableFullStreamDecompression();
        return this;
    }


    public Panshi maxInboundMessageSize(int bytes) {
        this.builder.maxInboundMessageSize(bytes);
        return this;
    }


    public Panshi maxInboundMetadataSize(int bytes) {
        this.builder.maxInboundMetadataSize(bytes);
        return this;
    }


    public Panshi keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        this.builder.keepAliveTime(keepAliveTime, timeUnit);
        return this;
    }


    public Panshi keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
        this.builder.keepAliveTimeout(keepAliveTimeout, timeUnit);
        return this;
    }


    public Panshi keepAliveWithoutCalls(boolean enable) {
        this.builder.keepAliveWithoutCalls(enable);
        return this;
    }


    public Panshi maxRetryAttempts(int maxRetryAttempts) {
        this.builder.maxRetryAttempts(maxRetryAttempts);
        return this;
    }


    public Panshi maxHedgedAttempts(int maxHedgedAttempts) {
        this.builder.maxHedgedAttempts(maxHedgedAttempts);
        return this;
    }


    public Panshi retryBufferSize(long bytes) {
        this.builder.retryBufferSize(bytes);
        return this;
    }


    public Panshi perRpcBufferLimit(long bytes) {
        this.builder.perRpcBufferLimit(bytes);
        return this;
    }


    public Panshi disableRetry() {
        this.builder.disableRetry();
        return this;
    }


    public Panshi enableRetry() {
        this.builder.enableRetry();
        return this;
    }


    public Panshi setBinaryLog(BinaryLog binaryLog) {
        this.builder.setBinaryLog(binaryLog);
        return this;
    }


    public Panshi maxTraceEvents(int maxTraceEvents) {
        this.builder.maxTraceEvents(maxTraceEvents);
        return this;
    }


    public Panshi proxyDetector(ProxyDetector proxyDetector) {
        this.builder.proxyDetector(proxyDetector);
        return this;
    }


    public Panshi defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        this.builder.defaultServiceConfig(serviceConfig);
        return this;
    }


    public Panshi disableServiceConfigLookUp() {
        this.builder.disableServiceConfigLookUp();
        return this;
    }


    public ManagedChannel build() {

        for (PanshiClientInterceptor clientInterceptor : polarisInterceptors) {
            clientInterceptor.init(this.sourceService.getNamespace(), this.sourceService.getService(), context);
            this.builder.intercept(clientInterceptor);
        }
        this.builder.intercept(interceptors);
        this.builder.defaultLoadBalancingPolicy(LOADBALANCER_PROVIDER);
        return builder.build();
    }

    private static String buildUrl(String target, ServiceInfo sourceService) {
        if (Objects.isNull(sourceService)) {
            return target;
        }

        String extendInfo = Base64.getUrlEncoder().encodeToString(JsonUtils.toJson(sourceService)
                .getBytes(StandardCharsets.UTF_8));

        if (target.contains("?")) {
            target += "&extend_info=" + extendInfo;
        } else {
            target += "?extend_info=" + extendInfo;
        }
        return target;
    }
}
