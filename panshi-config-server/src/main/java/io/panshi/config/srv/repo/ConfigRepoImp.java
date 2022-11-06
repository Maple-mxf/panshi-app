package io.panshi.config.srv.repo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Ordering;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.panshi.config.srv.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.panshi.config.srv.config.RepoClient.ETCD_CLIENT;


/**
 * Config的概念
 * Group -> Set -> ConfigId
 * /panshi-backend-server/online./key.json
 */
public class ConfigRepoImp {
    private ConfigRepoImp() {
        this.watchConfig();
    }

    public static final ConfigRepoImp instance = new ConfigRepoImp();

    private final static String ROOT_PREFIX = "/panshi/config";
    private final static String DELIMITER = "/";
    private final static ObjectMapper jsonMapper = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigRepoImp.class);

    public boolean putConfig(Config config) {
        ByteSequence key = ByteSequence.from(
                String.format("%s/%s/%s/%s",
                        ROOT_PREFIX, config.getGroup(), config.getSet(), config.getContent()),
                StandardCharsets.UTF_8);
        try {
            ByteSequence value = ByteSequence.from(jsonMapper.writeValueAsString(config), StandardCharsets.UTF_8);
            PutOption option = PutOption.newBuilder().withPrevKV().build();
            PutResponse response = ETCD_CLIENT.getKVClient().put(key, value, option).get();
            LOGGER.info("put config response {} ", response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("put config occur an exception {} config = {} ", e.getMessage(),
                    config);
        }

        return true;
    }

    public TreeMap<ConfigKey, List<Config>> getConfigList()
            throws ExecutionException, InterruptedException, TimeoutException {
        ByteSequence prefixKey = ByteSequence.from(ROOT_PREFIX, StandardCharsets.UTF_8);
        GetOption option = GetOption.newBuilder()
                .isPrefix(true)
                .withSortField(GetOption.SortTarget.KEY)
                .withSortOrder(GetOption.SortOrder.ASCEND)
                .build();
        CompletableFuture<GetResponse> future = ETCD_CLIENT.getKVClient().get(prefixKey, option);
        GetResponse response = future.get(5, TimeUnit.SECONDS);

        TreeMap<ConfigKey, List<Config>> configKeyListTreeMap = response.getKvs().stream()
                .map(kv -> {
                    try {
                        JsonNode node = jsonMapper.readValue(
                                kv.getValue().getBytes(),
                                JsonNode.class);
                        ConfigValueType configValueType = ConfigValueType.valueOf(
                                node.at("/configValueType").asText()
                        );
                        String group = node.at("/group").asText();
                        String set = node.at("/set").asText();
                        String configKey = node.at("/key").asText();
                        String configValue = node.at("/content").asText();

                        Config config;
                        if (ConfigValueType.JSON.equals(configValueType)) {
                            config = new JsonConfig();
                        } else if (ConfigValueType.YAML.equals(configValueType)) {
                            config = new YamlConfig();
                        } else if (ConfigValueType.PROPERTIES.equals(configValueType)) {
                            config = new PropertiesConfig();
                        } else {
                            return null;
                        }

                        config.setConfigValueType(configValueType);
                        config.setKey(configKey);
                        config.setContent(configValue);
                        config.setGroup(group);
                        config.setSet(set);
                        config.setVersion(kv.getVersion());

                        return config;

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(
                                t -> new ConfigKey(t.getGroup(), t.getSet(), t.getKey()),
                                () -> new TreeMap<ConfigKey, List<Config>>(Comparator.naturalOrder()),
                                Collectors.toList())
                );

        configKeyListTreeMap.forEach((key, values) ->
                Collections.<Config>sort(values, Ordering.from(Config::compareTo)));

        return configKeyListTreeMap;
    }

    public void watchConfig() {
        ByteSequence prefixKey = ByteSequence.from(ROOT_PREFIX.getBytes(StandardCharsets.UTF_8));
        WatchOption option = WatchOption.newBuilder().isPrefix(true).build();
        ETCD_CLIENT.getWatchClient().watch(prefixKey, option,
                watchResponse -> {
                    List<WatchEvent> events = watchResponse.getEvents();
                    for (WatchEvent event : events) {
                        try {
                            KeyValue keyValue = event.getKeyValue();
                            System.err.println(event.getEventType());
                            JsonNode jsonNode = jsonMapper.readValue(keyValue.getValue().getBytes(), JsonNode.class);
                            System.err.println(jsonNode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
