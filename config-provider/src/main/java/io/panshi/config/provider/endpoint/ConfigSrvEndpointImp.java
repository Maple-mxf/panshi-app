package io.panshi.config.provider.endpoint;

import io.grpc.stub.StreamObserver;
import io.panshi.config.srv.*;
import io.panshi.config.provider.handler.ConfigHandlerImp;
import io.panshi.config.provider.model.Config;

import java.util.List;
import java.util.stream.Collectors;

// Endpoint
public final class ConfigSrvEndpointImp extends ConfigSrvGrpc.ConfigSrvImplBase {

    @Override
    public void describeConfigList(DescribeConfigListRequest request,
                                   StreamObserver<DescribeConfigListResponse> observer) {
        List<Config> configs = ConfigHandlerImp.instance.describeConfigList();
        observer.onNext(DescribeConfigListResponse.newBuilder()
                .addAllConfigList(
                        configs.stream()
                                .map(t -> ConfigDto.newBuilder()
                                        .build())
                                .collect(Collectors.toList()))
                .build());
        observer.onCompleted();
    }
}
