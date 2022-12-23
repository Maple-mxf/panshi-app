package io.panshi.grpc.discovery;

import com.google.common.base.Strings;
import io.grpc.Server;
import io.grpc.ServerServiceDefinition;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.discovery.Provider;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.imp.discovery.ProviderImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PanshiGrpcServer extends Server {

    private static final Logger LOG = LoggerFactory.getLogger(PanshiGrpcServer.class);

    private final Config context;

    private final Provider provider;

    private final PanshiGrpcServerBuilder builder;

    private Server targetServer;

    private String host;

    private DelayRegister delayRegister = new NoopDelayRegister();

    private Duration maxWaitDuration;

    private RegisterHook registerHook;

    private final AtomicBoolean shutdownOnce = new AtomicBoolean(false);

    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setName("polaris-grpc-server");
        return t;
    });

    PanshiGrpcServer(PanshiGrpcServerBuilder builder, Config context, Server server) throws PanshiException {
        this.builder = builder;
        this.registerHook = builder.getRegisterHook();
        this.targetServer = server;
        this.context = context;
        this.provider = new ProviderImp(context);
    }

    @Override
    public Server start() throws IOException {
        initLocalHost();
        targetServer = targetServer.start();

        if (Objects.nonNull(delayRegister)) {
            executorService.execute(() -> {
                for (;;) {
                    if (delayRegister.allowRegis()) {
                        break;
                    }
                }

                this.registerInstance(targetServer.getServices());
            });
        }

        return this;
    }

    @Override
    public Server shutdown() {
        if (shutdownOnce.compareAndSet(false, true)) {
            executorService.shutdownNow();
            this.deregister(targetServer.getServices());
            provider.deregisterServiceInstance(); // TODO
        }

        if (builder.isOpenGraceOffline()) {
            return new GraceOffline(targetServer, maxWaitDuration).shutdown();
        }

        return targetServer.shutdown();
    }

    @Override
    public Server shutdownNow() {
        if (shutdownOnce.compareAndSet(false, true)) {
            executorService.shutdownNow();
            this.deregister(targetServer.getServices());
            provider.deregisterServiceInstance(); // TODO
        }
        return this.targetServer.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.targetServer.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.targetServer.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.targetServer.awaitTermination(timeout, unit);
    }

    @Override
    public void awaitTermination() throws InterruptedException {
        this.targetServer.awaitTermination();
    }

    public void setDelayRegister(DelayRegister delayRegister) {
        if (delayRegister == null) {
            return;
        }
        this.delayRegister = delayRegister;
    }

    public void setMaxWaitDuration(Duration maxWaitDuration) {
        this.maxWaitDuration = maxWaitDuration;
    }

    private void initLocalHost() {
        host = builder.getHost();
        if (!Strings.isNullOrEmpty(host)) {
            return;
        }

        // TODO
        String serverAddr = context.getEtcdConfig().getEndpoint();
        String[] detail = serverAddr.split(":");
        host = NetworkHelper.getLocalHost(detail[0], Integer.parseInt(detail[1]));
    }

    /**
     * This interface will determine whether it is an interface-level registration instance or an application-level
     * instance registration based on grpcServiceRegister.
     */
    private void registerInstance(List<ServerServiceDefinition> definitions) {
        if (!Strings.isNullOrEmpty(builder.getApplicationName())) {
            this.registerOne(builder.getApplicationName());
            return;
        }
        for (ServerServiceDefinition definition : definitions) {
            String grpcServiceName = definition.getServiceDescriptor().getName();
            this.registerOne(grpcServiceName);
        }
    }

    /**
     * Register a service instance.
     *
     * @param serviceName service name
     */
    private void registerOne(String serviceName) {

        provider.registerServiceInstance();

    }



    /**
     * Service deregister.
     *
     * @param definitions Definition of a service
     */
    private void deregister(List<ServerServiceDefinition> definitions) {

        // TODO
        provider.deregisterServiceInstance();
    }

}
