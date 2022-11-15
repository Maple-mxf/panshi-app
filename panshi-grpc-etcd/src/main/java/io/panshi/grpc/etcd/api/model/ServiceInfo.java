package io.panshi.grpc.etcd.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Deprecated
public class ServiceInfo {

    private String namespace;
    private String service;
    private String set;
    private String method;
}
