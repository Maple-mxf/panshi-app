package io.panshi.grpc.etcd.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Namespace {

    private String name;
    private String description;
    private LocalDateTime createTime;
}
