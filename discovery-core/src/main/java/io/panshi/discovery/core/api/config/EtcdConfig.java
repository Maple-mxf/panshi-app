package io.panshi.discovery.core.api.config;

import lombok.Data;

@Data
public class EtcdConfig {
    private String endpoint;
    private String namespace;
    private String user;
    private String password;
}
