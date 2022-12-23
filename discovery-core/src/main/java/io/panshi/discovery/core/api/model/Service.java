package io.panshi.discovery.core.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Deprecated
public class Service {
    private String name;
    private String namespace;
    private String set;
    private LocalDateTime createTime;
}
