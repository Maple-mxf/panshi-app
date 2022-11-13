package io.panshi.grpc.etcd.imp.balancing;

import io.panshi.grpc.etcd.api.balancing.LoadBalancer;
import io.panshi.grpc.etcd.api.model.Instance;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * 基于权重的轮询负载均衡算法
 * 优势：轮询均匀
 * 弊端：不会交叉轮询
 */
public class WeightRoundRobinLoadBalancer implements LoadBalancer {

    public WeightRoundRobinLoadBalancer(){}

    private final LongAdder count = new LongAdder();

    @Override
    public Instance selectOneInstance(List<Instance> instanceList) {
        if (instanceList == null || instanceList.isEmpty()){
            throw new IllegalArgumentException("instanceList must not be empty");
        }
        long totalWeight = instanceList.stream()
                .map(Instance::getWeight)
                .collect(Collectors.summarizingLong(value -> value))
                .getSum();
        count.increment();
        long sed = count.longValue() % totalWeight;
        for (Instance instance : instanceList) {
            if (!instance.isHealth())
                continue;
            if (sed<instance.getWeight()){
                return instance;
            }
            sed -= instance.getWeight();
        }
        return null;
    }
}
