package io.panshi.config.srv.model;


import com.fasterxml.jackson.databind.JsonNode;

public class JsonConfig extends Config<JsonNode> {

    public JsonConfig(){
        this.setConfigType(ConfigType.JSON);
    }
}
