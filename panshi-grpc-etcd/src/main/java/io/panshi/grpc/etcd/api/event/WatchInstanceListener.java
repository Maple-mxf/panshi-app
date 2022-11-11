package io.panshi.grpc.etcd.api.event;

@FunctionalInterface
public interface WatchInstanceListener {

    void handle(WatchInstanceEvent event);
}
