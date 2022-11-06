package io.panshi.config.srv.model;

import com.fasterxml.jackson.databind.JsonNode;

public class YamlConfig extends Config {
    public YamlConfig(){
        this.setConfigValueType(ConfigValueType.YAML);
    }
}
