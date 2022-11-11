package io.panshi.grpc.etcd.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RpcInvokeResult {
    private int code;
    private String errorText;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ServiceInfo serviceInfo;
    private Instance destInstance;
}
