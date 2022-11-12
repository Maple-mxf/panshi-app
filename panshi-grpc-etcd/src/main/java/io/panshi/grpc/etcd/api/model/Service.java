package io.panshi.grpc.etcd.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Service {
    private String name;
    private String namespace;
    private LocalDateTime createTime;
}
