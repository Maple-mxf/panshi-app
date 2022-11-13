package io.panshi.grpc.etcd.api.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Instance {

    private String namespace;
    private String service;
    private String set;
    private String ip;
    private int port;
    private int weight;
    private boolean isHealth;
    private Map<String,String> metadata;  // 服务元数据信息
    private String version; // 服务版本
    private String protocol = "grpc"; // 服务协议 目前仅实现grpc
    private LocalDateTime registryTime;  // 服务实例注册时间
}

