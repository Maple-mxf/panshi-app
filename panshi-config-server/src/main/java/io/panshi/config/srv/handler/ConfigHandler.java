package io.panshi.config.srv.handler;

import io.panshi.config.srv.model.Config;

import java.util.List;
import java.util.Optional;

public interface ConfigHandler {

    // 查询所有的配置列表
    List<Config> describeConfigList();

    // 查询某一个配置详情
    Optional<Config> describeConfig(String group, String set, String key, Long version);

    // 创建一个配置
    boolean createConfig(Config config);

}
