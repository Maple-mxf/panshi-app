package io.panshi.grpc.etcd.imp.balancing;

import io.panshi.grpc.etcd.api.balancing.LoadBalancer;
import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 基于权重的负载均衡算法
 */
public class WeightRandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();

    @Override
    public Instance selectOneInstance(List<Instance> instanceList) {
        if (instanceList == null || instanceList.isEmpty()){
            throw new IllegalArgumentException("instanceList must not be empty");
        }
        long totalWeight = instanceList.stream()
                .map(Instance::getWeight)
                .collect(Collectors.summarizingLong(value -> value))
                .getSum();

        int sed = random.nextInt((int) totalWeight);
        for (Instance instance : instanceList) {
            if (!instance.isHealth())
                continue;
            if (sed < instance.getWeight()){
                return instance;
            }
            sed -= instance.getWeight();
        }
        return null;
    }
}
