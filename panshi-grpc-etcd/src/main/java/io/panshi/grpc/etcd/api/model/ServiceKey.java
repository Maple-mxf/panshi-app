package io.panshi.grpc.etcd.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ServiceKey {
    private String namespace;
    private String service;
    private String set;
}
