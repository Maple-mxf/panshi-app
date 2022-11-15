package io.panshi.grpc.etcd.api.discovery;

import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.model.RpcInvokeResult;
import io.panshi.grpc.etcd.api.model.ServiceKey;

import javax.annotation.Nullable;
import java.util.List;

public interface Consumer extends DiscoveryClient {

    /**
     * 列出所有的instance
     * @param key service info
     * @return 健康的实例列表
     */
    List<Instance> listHealthInstances(ServiceKey key);

    /**
     * 通过负载均衡返回一个实例
     * @param key service info
     * @return health instance
     */
    @Nullable
    Instance getHealthInstance(ServiceKey key);

    // TODO 上报调用结果来进行统计
    // 参考opentelemetry https://opentelemetry.io/docs/collector/
    boolean collectInvokeMetric(RpcInvokeResult invokeResult);
}
