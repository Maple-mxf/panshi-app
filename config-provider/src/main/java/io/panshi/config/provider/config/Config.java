package io.panshi.config.provider.config;

import lombok.Data;

@Data
public class Config {

    private String[] etcdClusters;

    private AccountConfig[] accounts;
}
