package io.panshi.config.srv.model;

import java.util.Properties;

public class PropertiesConfig extends Config<Properties> {
    public PropertiesConfig(){
        this.setConfigType(ConfigType.PROPERTIES);
    }

}
