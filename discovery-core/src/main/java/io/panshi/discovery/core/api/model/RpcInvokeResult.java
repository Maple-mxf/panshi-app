package io.panshi.discovery.core.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RpcInvokeResult {
    private int code;
    private String errorText;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ServiceDefinition serviceDefinition;
    private Instance destInstance;
}
