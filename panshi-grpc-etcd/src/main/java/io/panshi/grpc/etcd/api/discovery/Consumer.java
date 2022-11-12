package io.panshi.grpc.etcd.api.discovery;

import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.api.model.RpcInvokeResult;
import io.panshi.grpc.etcd.api.model.ServiceKey;

import java.util.List;

public interface Consumer extends DiscoveryClient {

    List<Instance> listHealthInstances(ServiceKey key);

    // TODO 上报调用结果来进行统计
    // 参考opentelemetry https://opentelemetry.io/docs/collector/
    boolean collectInvokeMetric(RpcInvokeResult invokeResult);
}
