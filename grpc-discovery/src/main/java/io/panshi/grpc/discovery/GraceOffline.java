package io.panshi.grpc.discovery;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.ForwardingServerCall;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public final class GraceOffline {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraceOffline.class);

    private static final AtomicLong HANDLING_REQUEST_COUNT = new AtomicLong(0);

    private final Server grpcServer;

    private final Duration maxWaitDuration;

    private final AtomicBoolean executed = new AtomicBoolean(false);

    public GraceOffline(Server server, Duration maxWaitDuration) {
        this.grpcServer = server;
        this.maxWaitDuration = maxWaitDuration;
    }

    public Server shutdown() {
        if (!executed.compareAndSet(false, true)) {
            return grpcServer.shutdown();
        }

        long startTime = System.currentTimeMillis();
        long expectEndTime = startTime + maxWaitDuration.toMillis();

        LOGGER.info("[grpc-polaris] begin grace shutdown");

        try {
            // 等待 4 个 pull 时间间隔
            TimeUnit.SECONDS.sleep(4 * 2);
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }

        for (;;) {
            long noFinishRequestCount = HANDLING_REQUEST_COUNT.get();
            // 如果当前没有正在处理的请求了，退出，开始关闭
            if (noFinishRequestCount == 0) {
                break;
            }
            LOGGER.info("[grpc-polaris] still count=[{}] is handling", noFinishRequestCount);

            // 最多在等待 maxWaitDuration，否则直接执行 Server.shutdown()
            if (System.currentTimeMillis() > expectEndTime) {
                break;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
        return grpcServer.shutdown();
    }

    @VisibleForTesting
    public static long getCurrentTotalHandlingRequest() {
        return HANDLING_REQUEST_COUNT.get();
    }

    public static ServerInterceptor createInterceptor() {
        return new CountServerReceiveRequestInterceptor();
    }

    public static class CountServerReceiveRequestInterceptor implements ServerInterceptor {

        @Override
        public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
                ServerCallHandler<ReqT, RespT> next) {

            AtomicBoolean finish = new AtomicBoolean(false);

            Listener<ReqT> listener = next.startCall(
                    new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {
                        @Override
                        public void close(Status status, Metadata trailers) {
                            if (finish.compareAndSet(false, true)) {
                                HANDLING_REQUEST_COUNT.decrementAndGet();
                            }
                            super.close(status, trailers);
                        }
                    }
                    , metadata);

            return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {

                @Override
                public void onMessage(ReqT message) {
                    HANDLING_REQUEST_COUNT.incrementAndGet();
                    super.onMessage(message);
                }

                @Override
                public void onCancel() {
                    if (finish.compareAndSet(false, true)) {
                        HANDLING_REQUEST_COUNT.decrementAndGet();
                    }
                    super.onCancel();
                }

            };
        }
    }

}
