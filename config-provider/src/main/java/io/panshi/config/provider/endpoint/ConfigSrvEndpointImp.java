package io.panshi.config.provider.endpoint;

import io.grpc.stub.StreamObserver;
import io.panshi.config.provider.handler.ConfigHandlerImp;
import io.panshi.config.provider.model.Config;
import io.panshi.config.srv.ConfigDto;
import io.panshi.config.srv.ConfigSrvGrpc;
import io.panshi.config.srv.ListConfigRequest;
import io.panshi.config.srv.ListConfigResponse;

import java.util.List;
import java.util.stream.Collectors;

// Endpoint
public final class ConfigSrvEndpointImp extends ConfigSrvGrpc.ConfigSrvImplBase {
    @Override
    public void listConfig(ListConfigRequest request, StreamObserver<ListConfigResponse> observer) {
        List<Config> configs = ConfigHandlerImp.instance.describeConfigList();
        observer.onNext(ListConfigResponse.newBuilder()
                .addAllConfigList(
                        configs.stream()
                                .map(t -> ConfigDto.newBuilder()
                                        .build())
                                .collect(Collectors.toList()))
                .build());
        observer.onCompleted();
    }

}
