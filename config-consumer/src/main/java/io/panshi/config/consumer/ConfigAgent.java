package io.panshi.config.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.panshi.config.srv.CommonResp;
import io.panshi.config.srv.ConfigDto;
import io.panshi.config.srv.ConfigSrvGrpc;
import io.panshi.config.srv.ErrCode;
import io.panshi.config.srv.PullConfigListRequest;
import io.panshi.config.srv.PullConfigListResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class ConfigAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigAgent.class);

    private final ConfigSrvGrpc.ConfigSrvBlockingStub configSrvBlockingStub ;
    private final ListeningScheduledExecutorService executor = MoreExecutors.listeningDecorator(
            Executors.newScheduledThreadPool(2, new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setNameFormat("config-agent-%d")
                            .setPriority(1)
                    .build())
    );
    private final static String GROUP = "SERVICE_GROUP";
    private final static String SET = "SERVICE_SET";

    // 配置文件持久化的位置
    private final static String CONF_LOCATION = "/app/conf";

    private final String group;
    private final String set;

    private final static ObjectMapper jsonMapper = new ObjectMapper();
    private final static ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public final static ConfigAgent instance = new ConfigAgent();

    private final ConcurrentHashMap<String, ConfigDto> configMap
            = new ConcurrentHashMap<>();

    private ConfigAgent(){
        group = System.getenv(GROUP);
        set = System.getenv(SET);
        if (group == null || group .isEmpty()){
            throw new IllegalStateException("'SERVICE_GROUP' env var absent ");
        }
        if (set == null || set .isEmpty()){
            throw new IllegalStateException("'SERVICE_SET' env var absent ");
        }

//        ManagedChannelBuilder.forTarget()

        Channel channel = ManagedChannelBuilder
                .forAddress("", 8090)
                .build();

         configSrvBlockingStub = ConfigSrvGrpc.newBlockingStub(channel);
    }

    private void writeConfigFile(ConfigDto config){
        switch (config.getConfigValueType()){
            case "JSON":
                try {
                    JsonNode jsonNode = jsonMapper.readValue(config.getContent().getBytes(StandardCharsets.UTF_8),
                            JsonNode.class);
                    jsonMapper.writeValue(
                            new File(String.format("%s/%s",CONF_LOCATION,config.getKey())),
                            jsonNode
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "YAML":
                try {
                    JsonNode jsonNode = yamlMapper.readValue(config.getContent().getBytes(StandardCharsets.UTF_8),
                            JsonNode.class);
                    yamlMapper.writeValue(
                            new File(String.format("%s/%s",CONF_LOCATION,config.getKey())),
                            jsonNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "PROPERTIES":
                Properties properties = new Properties();
                try {
                    properties.load(new ByteArrayInputStream(config.getContent().getBytes(StandardCharsets.UTF_8)));

                    byte[] configBytes = properties.keySet().stream().map(key -> {
                                String property = properties.getProperty(String.valueOf(key), "");
                                return String.format("%s=%s", key, property);
                            }).collect(Collectors.joining("\n"))
                            .getBytes(StandardCharsets.UTF_8);

                    Files.write(configBytes, new File(String.format("%s/%s",CONF_LOCATION,config.getKey())));
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("write properties in the {} file failed, config = {} , message {}  ",
                            String.format("%s/%s",CONF_LOCATION,config.getKey()),config, e.getMessage());
                }

                break;
        }
    }

    public void start(){
        // 提交获取配置列表任务
        ListenableScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            PullConfigListRequest request = PullConfigListRequest
                    .newBuilder()
                    .setGroup(group)
                    .setSet(set)
                    .build();
            PullConfigListResponse response =
                    configSrvBlockingStub.pullConfigList(request);

            CommonResp cRsp = response.getCRsp();

            // 如果请求成功
            if (ErrCode.SUCCESS .equals(cRsp.getCode() )){
                List<ConfigDto> configList = response.getConfigListList();

                for (ConfigDto config : configList) {

                    ConfigDto oldConfig = configMap.get(config.getKey());

                    // 1 配置没有发生变化，忽略
                    if (oldConfig != null && oldConfig.getVersion() == config.getVersion()){
                        continue;
                    }

                    // 2 持久化配置
                    writeConfigFile(config);

                    // 3 覆盖之前的配置
                    configMap.put(config.getKey(), config);
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        Futures.addCallback(future, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(Throwable e) {

            }
        },executor);
    }

    public void stop(){
        executor.shutdown();
    }

}
