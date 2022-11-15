package io.panshi.discovery.core.api.event;

import io.panshi.discovery.core.api.model.Instance;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WatchInstanceEvent {

    public enum Type{
        REGISTER,
        DEREGISTER
    }
    private Type type;
    private Instance instance;
    private LocalDateTime time;
}
