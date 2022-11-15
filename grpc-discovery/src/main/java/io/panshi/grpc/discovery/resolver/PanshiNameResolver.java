package io.panshi.grpc.discovery.resolver;

import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.discovery.Consumer;
import io.panshi.discovery.core.api.event.WatchServiceEvent;
import io.panshi.discovery.core.api.event.WatchServiceListener;
import io.panshi.discovery.core.api.model.Instance;
import io.panshi.discovery.core.api.model.ServiceKey;
import io.panshi.grpc.discovery.constants.GrpcDiscoveryConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @see Consumer
 */
public final class PanshiNameResolver extends NameResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanshiNameResolver.class);

    private final String namespace;
    private final String service;
    private final String host;
    private final Consumer consumer;
    private final String set;

    public PanshiNameResolver(URI targetURI, Config config, Consumer consumer) {
        this.host = targetURI.getHost();
        this.namespace = config.getNamespace().orElseThrow(()->new IllegalArgumentException(""));
        this.service = config.getService().orElseThrow(()->new IllegalArgumentException(""));
        this.set = Objects.requireNonNull(config.getSet()).orElse("default");
        this.consumer = consumer;
    }

    @Override
    public String getServiceAuthority() {
        return null;
    }

    @Override
    public void start(Listener2 listener) {
        GrpcWatchServiceListenerImp watchServiceListenerImp = new GrpcWatchServiceListenerImp(listener);
        consumer.registerListener(watchServiceListenerImp);
        LOGGER.info("panshi etcd name resolver component start success");
    }

    @Override
    public void shutdown() {
        this.consumer.stop();
    }

    private class GrpcWatchServiceListenerImp implements WatchServiceListener {
        private final Listener2 listener2;
        GrpcWatchServiceListenerImp(Listener2 listener2){
            this.listener2 = listener2;
        }
        @Override
        public void handle(WatchServiceEvent event) {
            LOGGER.info("receive service instance change event {} ",event);
            ServiceKey serviceKey = new ServiceKey();
            serviceKey.setNamespace(namespace);
            serviceKey.setService(service);
            serviceKey.setSet(set);
            List<Instance> instanceList = consumer.listHealthInstances(serviceKey);
            notifyListener(listener2, instanceList);
        }
    }

    private void notifyListener(Listener2 listener, List<Instance> instanceList ) {
        if (!instanceList.isEmpty()) {
            List<EquivalentAddressGroup> equivalentAddressGroups = instanceList
                    .stream()
                    .map(this::buildEquivalentAddressGroup)
                    .collect(Collectors.toList());

            Attributes.Builder builder = Attributes.newBuilder();
            listener.onResult(ResolutionResult.newBuilder()
                    .setAddresses(equivalentAddressGroups)
                    .setAttributes(builder.build())
                    .build());
        }
    }

    private EquivalentAddressGroup buildEquivalentAddressGroup(Instance instance) {
        InetSocketAddress address = new InetSocketAddress(instance.getHost(), instance.getPort());
        Attributes attributes = Attributes.newBuilder()
                .set(GrpcDiscoveryConstants.INSTANCE_KEY, instance)
                .set(GrpcDiscoveryConstants.TARGET_NAMESPACE_KEY, namespace)
                .set(GrpcDiscoveryConstants.TARGET_SERVICE_KEY, host)
                .build();
        return new EquivalentAddressGroup(address, attributes);
    }

}
