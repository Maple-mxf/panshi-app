package io.panshi.config.srv.config;


import io.etcd.jetcd.Client;

public class RepoClient {
    private RepoClient(){}

    public static final Client ETCD_CLIENT;

    private static Config config = new Config();

    static {
        ETCD_CLIENT = Client.builder()
                .endpoints(config.getEtcdClusters())
                .build();
    }
}
