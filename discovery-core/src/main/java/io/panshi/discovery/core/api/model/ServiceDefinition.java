package io.panshi.discovery.core.api.model;

import lombok.Data;

// definition
@Data
public class ServiceDefinition {
    private String namespace;
    private String application;
    private String service;
    private String set;
}
