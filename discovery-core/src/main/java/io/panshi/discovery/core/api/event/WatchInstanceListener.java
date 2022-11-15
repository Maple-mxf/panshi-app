package io.panshi.discovery.core.api.event;

@FunctionalInterface
public interface WatchInstanceListener {

    void handle(WatchInstanceEvent event);
}
