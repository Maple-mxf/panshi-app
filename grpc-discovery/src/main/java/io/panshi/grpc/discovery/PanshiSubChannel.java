package io.panshi.grpc.discovery;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.LoadBalancer;
import io.panshi.discovery.core.api.model.Instance;

public class PanshiSubChannel extends LoadBalancer.Subchannel implements Comparable<PanshiSubChannel> {
    private final LoadBalancer.Subchannel channel;
    private final Instance instance;

    public PanshiSubChannel(LoadBalancer.Subchannel channel, Instance instance ) {
        Preconditions.checkNotNull(channel);
        Preconditions.checkNotNull(instance);
        this.channel = channel;
        this.instance = instance;
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

    public Instance getInstance(){
        return instance;
    }

    // TODO
    @Override
    public int compareTo(PanshiSubChannel o) {
        return this.instance.compareTo(o.instance);
    }
}
