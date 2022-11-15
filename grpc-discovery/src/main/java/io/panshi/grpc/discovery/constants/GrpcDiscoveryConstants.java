package io.panshi.grpc.discovery.constants;

import io.grpc.Attributes;
import io.panshi.discovery.core.api.model.Instance;

public class GrpcDiscoveryConstants {
    public final static Attributes.Key<Instance> INSTANCE_KEY = Attributes.Key.create(Instance.class.getName());
    public final static Attributes.Key<String> TARGET_NAMESPACE_KEY = Attributes.Key.create("PANSHI_SOURCE_NAMESPACE");
    public final static Attributes.Key<String> TARGET_SERVICE_KEY = Attributes.Key.create("PANSHI_SOURCE_SERVICE");
    public final static String LOADBALANCER_PROVIDER = "panshi";
}



