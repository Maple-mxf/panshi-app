package io.panshi.grpc.discovery;

import com.google.common.collect.Maps;
import io.grpc.Attributes;
import io.grpc.LoadBalancer;
import io.panshi.discovery.core.api.discovery.Consumer;
import io.panshi.discovery.core.api.model.Instance;
import io.panshi.discovery.core.api.model.ServiceDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @see LoadBalancer
 * @see io.grpc.LoadBalancer.SubchannelPicker
 */
public class PanshiSubChannelPicker extends LoadBalancer.SubchannelPicker {
    private final Consumer consumer;
    private final Map<Instance,PanshiSubChannel> subChannelsMap;
    private final Attributes attributes;
    private final ServiceDefinition serviceDefinition;
    private io.panshi.discovery.core.api.balancing.LoadBalancer loadBalancer;

    public PanshiSubChannelPicker(Consumer consumer,
                                  List<PanshiSubChannel> subChannels,
                                  Attributes attributes,
                                  ServiceDefinition serviceDefinition) {
        this.consumer = consumer;
        this.subChannelsMap = subChannels.stream()
                .map(t-> Maps.immutableEntry(t.getInstance(),t))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(o1,o2)->o1));
        this.attributes = attributes;
        this.serviceDefinition = serviceDefinition;
    }


    @Override
    public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {

        if (this.subChannelsMap.isEmpty()){
            return LoadBalancer.PickResult.withNoResult();
        }

        String namespace = attributes.get(GrpcDiscoveryConstants.TARGET_NAMESPACE_KEY);
        String service = attributes.get(GrpcDiscoveryConstants.TARGET_SERVICE_KEY);

        // 通过客户端负载均衡选择一个实例
        Instance instance = loadBalancer.selectOneInstance(new ArrayList<>(subChannelsMap.keySet()));

       return  LoadBalancer.PickResult.withSubchannel(
              subChannelsMap.get(instance)
       );

    }
}