package io.panshi.grpc.discovery;

import io.grpc.Attributes;
import io.grpc.LoadBalancer;

public class PanshiSubChannel extends LoadBalancer.Subchannel {
    private final LoadBalancer.Subchannel channel;

    public PanshiSubChannel(LoadBalancer.Subchannel channel) {
        this.channel = channel;
    }

    @Override
    public void shutdown() {
        channel.shutdown();
    }

    @Override
    public void requestConnection() {
        channel.requestConnection();
    }

    @Override
    public Attributes getAttributes() {
        return channel.getAttributes();
    }
}
