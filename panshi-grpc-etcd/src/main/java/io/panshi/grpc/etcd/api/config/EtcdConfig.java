package io.panshi.grpc.etcd.api.config;

import lombok.Data;

@Data
public class EtcdConfig {
    private String[] endpoints;
    private String namespace;
    private String user;
    private String password;
}
