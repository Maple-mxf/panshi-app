package io.panshi.config.srv.endpoint;

import io.grpc.stub.StreamObserver;
import io.panshi.config.srv.*;
import io.panshi.config.srv.handler.ConfigHandlerImp;
import io.panshi.config.srv.model.Config;

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
