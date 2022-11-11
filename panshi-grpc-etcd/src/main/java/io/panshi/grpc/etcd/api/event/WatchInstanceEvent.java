package io.panshi.grpc.etcd.api.event;

import io.panshi.grpc.etcd.api.model.Instance;
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
