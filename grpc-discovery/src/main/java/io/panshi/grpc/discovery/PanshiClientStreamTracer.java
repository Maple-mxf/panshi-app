package io.panshi.grpc.discovery;

import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * grpc 调用的 tracer 信息，记录每次 grpc 调用的情况
 * 1. 每次请求的相应时间
 * 2. 每次请求的结果，记录成功或者失败
 */
public class PanshiClientStreamTracer extends ClientStreamTracer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanshiClientStreamTracer.class);

    private final StreamInfo streamInfo;
    private final ClientCallInfo info;

    private final long startTime = System.currentTimeMillis();

    private final AtomicBoolean reported = new AtomicBoolean(false);

    public PanshiClientStreamTracer(StreamInfo streamInfo, ClientCallInfo info) {
        this.streamInfo = streamInfo;
        this.info = info;
    }

    @Override
    public void streamCreated(Attributes transportAttrs, Metadata headers) {
        super.streamCreated(transportAttrs, headers);
    }

    @Override
    public void streamClosed(Status status) {
        super.streamClosed(status);
    }

    @Override
    public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        super.inboundMessageRead(seqNo, optionalWireSize, optionalUncompressedSize);
    }
}
