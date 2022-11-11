package io.panshi.grpc.etcd.imp.config;

import io.panshi.grpc.etcd.api.config.Config;

public class ConfigImp implements Config {

    @Override
    public String[] getEtcdConnectionStrings() {
        return new String[0];
    }
}
