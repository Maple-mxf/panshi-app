package io.panshi.config.srv.model;


import lombok.Data;

@Data
public abstract class Config<T>{

    private String group;

    private String key;

    private ConfigType configType;

    private T content;


}
