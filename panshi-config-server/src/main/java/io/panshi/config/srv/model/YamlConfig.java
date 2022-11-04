package io.panshi.config.srv.model;

import com.fasterxml.jackson.databind.JsonNode;

public class YamlConfig extends Config<JsonNode> {
    public YamlConfig(){
        this.setConfigType(ConfigType.YAML);
    }
}
