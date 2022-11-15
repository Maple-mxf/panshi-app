package io.panshi.discovery.core.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Namespace {
    private String name;
    private LocalDateTime createTime;
}
