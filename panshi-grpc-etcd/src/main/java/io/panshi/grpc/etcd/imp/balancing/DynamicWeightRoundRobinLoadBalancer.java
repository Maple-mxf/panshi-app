package io.panshi.grpc.etcd.imp.balancing;

import io.panshi.grpc.etcd.api.balancing.LoadBalancer;
import io.panshi.grpc.etcd.api.model.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于轮询算法的平滑加权算法
 * 实现思路：所选中的服务器的权重减去所有机器的权重加起来的总值，作为接下来的动态权重，没被选中的服务器权重不变
 * 优势：可以达到交叉选取的效果
 * https://www.cnblogs.com/jojop/p/13997273.html
 */
public class DynamicWeightRoundRobinLoadBalancer implements LoadBalancer {

    private final static Logger LOGGER = LoggerFactory.getLogger(
            DynamicWeightRoundRobinLoadBalancer.class
    );

    @Override
    public Instance selectOneInstance(List<Instance> instanceList) {

        // 0 设置所有实例的动态权重
        for (Instance instance : instanceList) {
            instance.setDynamicWeight(
                    instance.getWeight()+instance.getDynamicWeight()
            );
        }

        // 1 选取权重最大的实例
        Instance maxWeightInstance = null;
        for (Instance instance : instanceList) {
            if (maxWeightInstance == null ||
                    instance.getDynamicWeight() > maxWeightInstance.getDynamicWeight()){
                maxWeightInstance = instance;
            }
        }

        // 2 设置最大权重机器的动态权重 服务器的权重减去所有机器的权重加起来的总值
        if (maxWeightInstance != null){
            long totalWeight = instanceList.stream()
                    .map(Instance::getDynamicWeight)
                    .collect(Collectors.summarizingLong(value -> value))
                    .getSum();
            maxWeightInstance.setDynamicWeight(
                    maxWeightInstance.getDynamicWeight() - totalWeight
            );
        }

        LOGGER.error("not found any instance ");

        return maxWeightInstance;
    }
}
