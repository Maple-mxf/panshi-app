package io.panshi.discovery.core.api.discovery;

import lombok.Data;

@Data
public class RegisterInstanceRequest {
    private String namespace;
    private String application;
    private String service;
    private String set;
    private int port;
    private int weight;
    private String version;
}
