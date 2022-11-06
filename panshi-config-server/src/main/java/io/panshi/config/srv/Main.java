package io.panshi.config.srv;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.panshi.config.srv.endpoint.ConfigSrvEndpointImp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8081)
                .addService(new ConfigSrvEndpointImp())
                .build();

        server.start();
        server.awaitTermination();
    }
}
