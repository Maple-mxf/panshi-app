package io.panshi.config.provider.handler;

import io.panshi.config.provider.model.Config;
import io.panshi.config.provider.model.ConfigKey;
import io.panshi.config.provider.repo.ConfigRepoImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ConfigHandlerImp implements ConfigHandler {
    private ConfigHandlerImp() {
    }

    public static final ConfigHandlerImp instance = new ConfigHandlerImp();

    @Override
    public List<Config> describeConfigList() {
        try {
            TreeMap<ConfigKey, List<Config>> configListMap = ConfigRepoImp.instance.getConfigList();
            return configListMap.keySet().stream()
                    .map(key -> {
                        List<Config> configList = configListMap.get(key);
                        return configList.get(configList.size() - 1);
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Config> describeConfig(String group, String set, String key, Long version) {
        return Optional.empty();
    }

    @Override
    public boolean createConfig(Config config) {
        return false;
    }
}
