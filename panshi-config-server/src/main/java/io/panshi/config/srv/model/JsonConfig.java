package io.panshi.config.srv.model;


import com.fasterxml.jackson.databind.JsonNode;

public class JsonConfig extends Config {

    public JsonConfig(){
        this.setConfigValueType(ConfigValueType.JSON);
    }
}
