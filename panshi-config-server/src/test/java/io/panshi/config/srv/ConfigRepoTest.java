package io.panshi.config.srv;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.panshi.config.srv.model.Config;
import io.panshi.config.srv.model.ConfigKey;
import io.panshi.config.srv.model.JsonConfig;
import io.panshi.config.srv.repo.ConfigRepoImp;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ConfigRepoTest {

    private ConfigRepoImp configRepoImp;
    private ObjectMapper jsonMapper;

    @Before
    public void setup() throws ClassNotFoundException {
        Class.forName("io.panshi.config.srv.repo.ConfigRepoImp");
        configRepoImp = ConfigRepoImp.instance;
        jsonMapper = new ObjectMapper();
    }

    @Test
    public void testPutConfig() throws InterruptedException {

        Config config = new JsonConfig();
        config.setKey("Console.json");
        config.setGroup("PanshiBackendServer");
        config.setSet("online.*");
        config.setContent("[]");

        boolean putResult = configRepoImp.putConfig(config);
        System.err.println(putResult);


    }

    @Test
    public void getConfigList() throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        TreeMap<ConfigKey, List<Config>> configList = configRepoImp.getConfigList();
        System.err.println(jsonMapper.writeValueAsString(configList));
    }

    @Test
    public void testWatchConfig() throws InterruptedException, ClassNotFoundException {

        Thread.sleep(10000000);
    }
}
