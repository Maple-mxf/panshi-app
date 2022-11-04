package io.panshi.config.srv;

import io.grpc.stub.StreamObserver;

// Endpoint
public final class ConfigSrvImp extends ConfigSrvGrpc.ConfigSrvImplBase {

    ConfigSrvImp(){}

    @Override
    public void describeConfigDetail(DescribeConfigDetailRequest request,
                                     StreamObserver<DescribeConfigDetailResponse> observer) {
    }

    @Override
    public void createConfig(CreateConfigReq request, StreamObserver<CreateConfigResp> observer) {
        observer.onNext();
        observer.onCompleted();
    }
}
