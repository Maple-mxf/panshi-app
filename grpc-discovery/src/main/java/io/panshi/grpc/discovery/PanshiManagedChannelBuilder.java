package io.panshi.grpc.discovery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.BinaryLog;
import io.grpc.ClientInterceptor;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ProxyDetector;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.model.ServiceInfo;
import io.panshi.discovery.core.imp.config.ConfigImp;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PanshiManagedChannelBuilder {
    private static final Config CONFIG = ConfigImp.init();

    static {
        PanshiNameResolverFactory.init(CONFIG);
    }

    private final ManagedChannelBuilder<?> builder;
    private final List<PanshiClientInterceptor> panshiClientInterceptors = new ArrayList<>();
    private final List<ClientInterceptor> interceptors = new ArrayList<>();

    private final ServiceInfo sourceService;
    private PanshiManagedChannelBuilder(String target,ServiceInfo sourceService){
        this.builder = ManagedChannelBuilder.forTarget(buildUrl(target,sourceService));
        this.sourceService = sourceService;
    }
    public static PanshiManagedChannelBuilder forTarget(String target){
        return new PanshiManagedChannelBuilder(target,null);
    }

    public static PanshiManagedChannelBuilder forTarget(String target,ServiceInfo sourceService){
        return new PanshiManagedChannelBuilder(target,sourceService);
    }


    public PanshiManagedChannelBuilder directExecutor(){
        builder.directExecutor();
        return this;
    }

    public PanshiManagedChannelBuilder executor(Executor executor){
        builder.executor(executor);
        return this;
    }

    public PanshiManagedChannelBuilder intercept(List<ClientInterceptor> interceptors)
    {
        for (ClientInterceptor interceptor : interceptors) {
            if (interceptor instanceof PanshiClientInterceptor){
                this.panshiClientInterceptors.add((PanshiClientInterceptor) interceptor);
            }else{
                this.interceptors.add(interceptor);
            }
        }
        return this;
    }

    public PanshiManagedChannelBuilder intercept(ClientInterceptor... interceptors){
        return intercept(Arrays.asList(interceptors));
    }

    public PanshiManagedChannelBuilder userAgent(String userAgent){
        builder.userAgent(userAgent);
        return this;
    }


    public PanshiManagedChannelBuilder overrideAuthority(String authority ){
        builder.overrideAuthority(authority);
        return this;
    }

    public PanshiManagedChannelBuilder decompressorRegistry(DecompressorRegistry registry){
        builder.decompressorRegistry(registry);
        return this;
    }

    public PanshiManagedChannelBuilder compressorRegistry(CompressorRegistry registry){
        builder.compressorRegistry(registry);
        return this;
    }

    public PanshiManagedChannelBuilder idleTimeout(long value, TimeUnit unit){
        builder.idleTimeout(value,unit);
        return this;
    }

    public PanshiManagedChannelBuilder offloadExecutor(Executor executor){
        this.builder.offloadExecutor(executor);
        return this;
    }

    public PanshiManagedChannelBuilder usePlaintext(){
        this.builder.usePlaintext();
        return this;
    }

    public PanshiManagedChannelBuilder useTransportSecurity(){
        this.builder.useTransportSecurity();
        return this;
    }

    public PanshiManagedChannelBuilder enableFullStreamDecompression(){
        this.builder.enableFullStreamDecompression();
        return this;
    }

    public PanshiManagedChannelBuilder maxInboundMessageSize(int maxSize){
        this.builder.maxInboundMessageSize(maxSize);
        return this;
    }

    public PanshiManagedChannelBuilder maxInboundMetadataSize(int maxSize){
        this.builder.maxInboundMetadataSize(maxSize);
        return this;
    }

    public PanshiManagedChannelBuilder keepAliveTime(long keepAliveTime, TimeUnit timeUnit){
        this.builder.keepAliveTime(keepAliveTime,timeUnit);
        return this;
    }

    public PanshiManagedChannelBuilder keepAliveTimeout(long keepAliveTime, TimeUnit timeUnit){
        this.builder.keepAliveTimeout(keepAliveTime,timeUnit);
        return this;
    }

    public PanshiManagedChannelBuilder keepAliveWithoutCalls(boolean enable){
        this.builder.keepAliveWithoutCalls(enable);
        return this;
    }

    public PanshiManagedChannelBuilder maxRetryAttempts(int maxRetryAttempts){
        this.builder.maxRetryAttempts(maxRetryAttempts);
        return this;
    }

    public PanshiManagedChannelBuilder retryBufferSize(long size){
        this.builder.retryBufferSize(size);
        return this;
    }

    public PanshiManagedChannelBuilder perRpcBufferLimit(long limit){
        this.builder.perRpcBufferLimit(limit);
        return this;
    }

    public PanshiManagedChannelBuilder disableRetry(){
        this.builder.disableRetry();
        return this;
    }

    public PanshiManagedChannelBuilder enableRetry(){
        this.builder.enableRetry();
        return this;
    }

    public PanshiManagedChannelBuilder setBinaryLog(BinaryLog binaryLog){
        this.builder.setBinaryLog(binaryLog);
        return this;
    }

    public PanshiManagedChannelBuilder maxTraceEvents(int maxTraceEvents){
        this.builder.maxTraceEvents(maxTraceEvents);
        return this;
    }

    public PanshiManagedChannelBuilder proxyDetector(ProxyDetector proxyDetector){
        this.builder.proxyDetector(proxyDetector);
        return this;
    }

    public PanshiManagedChannelBuilder defaultServiceConfig(Map<String,?> serviceConfig){
        this.builder.defaultServiceConfig(serviceConfig);
        return this;
    }

    public PanshiManagedChannelBuilder disableServiceConfigLookup(){
        this.builder.disableServiceConfigLookUp();
        return this;
    }

    public ManagedChannel build(){
        for (PanshiClientInterceptor clientInterceptor : panshiClientInterceptors) {
            clientInterceptor.init(this.sourceService.getNamespace(),
                    this.sourceService.getService(),
                    CONFIG
                    );
            this.builder.intercept(clientInterceptor);
        }
        this.builder.intercept(interceptors);
        // TODO 设置负载均衡策略
//        this.builder.defaultLoadBalancingPolicy("")
        return builder.build();
    }

    private static String buildUrl(String target, ServiceInfo sourceService) {
        if (Objects.isNull(sourceService)) {
            return target;
        }
        String extendInfo;
        try {
            extendInfo = Base64.getUrlEncoder().encodeToString(new ObjectMapper().writeValueAsString(sourceService)
                    .getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (target.contains("?")) {
            target += "&extend_info=" + extendInfo;
        } else {
            target += "?extend_info=" + extendInfo;
        }

        return target;
    }







}
