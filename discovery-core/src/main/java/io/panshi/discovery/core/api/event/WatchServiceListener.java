package io.panshi.discovery.core.api.event;

@FunctionalInterface
public interface WatchServiceListener {

    void handle(WatchServiceEvent event);
}
