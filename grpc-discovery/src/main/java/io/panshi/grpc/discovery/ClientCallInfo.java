package io.panshi.grpc.discovery;

import io.panshi.discovery.core.api.discovery.Consumer;
import io.panshi.discovery.core.api.model.Instance;

public class ClientCallInfo {
    private final String method;
    private final Instance instance;
    private final Consumer consumer;
    private final String targetNamespace;
    private final String targetService;

    public ClientCallInfo(String method, Instance instance,
                          Consumer consumer,
                          String targetNamespace,
                          String targetService) {
        this.method = method;
        this.instance = instance;
        this.consumer = consumer;
        this.targetNamespace = targetNamespace;
        this.targetService = targetService;
    }

    public String getMethod() {
        return method;
    }

    public Instance getInstance() {
        return instance;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public String getTargetService() {
        return targetService;
    }
}
