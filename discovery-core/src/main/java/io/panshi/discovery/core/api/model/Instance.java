package io.panshi.discovery.core.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Instance {
    private String namespace;
    private String service;
    private String set; // 可以为空，默认为default
    private String host;
    private int port;
    private long weight; // 开发者配置的静态权重参数检查，必须大于0，在客户端负载均衡的时候可以做为动态权重使用
    @JsonIgnore private transient long dynamicWeight; // 动态权重
    private boolean isHealth;
    private Map<String,String> metadata;  // 服务元数据信息
    private String version; // 服务版本
    private String protocol = "grpc"; // 服务协议 目前仅实现grpc
    private LocalDateTime registryTime;  // 服务实例注册时间
}

