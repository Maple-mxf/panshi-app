package io.panshi.config.provider.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.reflect.ClassPath;
import io.etcd.jetcd.Client;

import java.io.IOException;

public class RepoClient {
    private RepoClient() {
    }

    public static final Client ETCD_CLIENT;

    private static final Config config;

    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    static {
        try {

            // 1 读取加载配置
            byte[] configSrc = ClassPath.from(RepoClient.class.getClassLoader()).getResources()
                    .stream().filter(t ->
                            t.getResourceName().equals("conf.yaml") ||
                                    t.getResourceName().equals("conf.yml")
                    )
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("not found conf.yaml"))
                    .asByteSource().read();

            config = yamlMapper.readValue(configSrc, Config.class);
            ETCD_CLIENT = Client.builder()
                    .endpoints(config.getEtcdClusters())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
