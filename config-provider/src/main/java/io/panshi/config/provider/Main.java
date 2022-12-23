package io.panshi.config.provider;

import io.grpc.Server;
import io.panshi.config.provider.endpoint.ConfigSrvEndpointImp;
import io.panshi.grpc.discovery.PanshiGrpcServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = PanshiGrpcServerBuilder.forPort(9000)
                .addService(new ConfigSrvEndpointImp()).build();
        server.start();
        server.awaitTermination();
    }
}
