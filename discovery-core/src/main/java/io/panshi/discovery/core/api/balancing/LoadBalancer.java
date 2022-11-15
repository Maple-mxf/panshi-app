package io.panshi.discovery.core.api.balancing;

import io.panshi.discovery.core.api.model.Instance;

import java.util.List;

public interface LoadBalancer {

    Instance selectOneInstance(List<Instance> instanceList);
}
