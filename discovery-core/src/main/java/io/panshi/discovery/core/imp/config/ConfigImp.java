package io.panshi.discovery.core.imp.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.reflect.ClassPath;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.config.EtcdConfig;
import io.panshi.discovery.core.api.exception.ErrorCode;
import io.panshi.discovery.core.api.exception.PanshiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class ConfigImp implements Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigImp.class);
    private static final String CONFIG_CLASSPATH_1 = "panshi.yaml";
    private static final String CONFIG_CLASSPATH_2 = "panshi.yml";
    private static final String DEFAULT_NAMESPACE = "default";
    private static final String DEFAULT_SET = "default";
    private static final String CONFIG_LOCATION_ENV_PATH_KEY = "CONFIG_FILE_PATH";
    private static final String ETCD_HOST = "ETCD_HOST"; // require
    private static final String ETCD_PORT = "ETCD_PORT"; // require
    private static final String ETCD_NAMESPACE = "ETCD_NAMESPACE"; // optional
    private static final String ETCD_USERNAME = "ETCD_USERNAME"; // optional
    private static final String ETCD_PASSWORD = "ETCD_PASSWORD"; // optional
    private static final String NAMESPACE = "NAMESPACE"; // optional
    private static final String APPLICATION_NAME = "APPLICATION_NAME"; // require
    private static final String SET = "SET"; // optional
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());
    private static final ConfigReader ENV_VAR_FILE_CR = new EnvVarFileConfigReader();
    private static final ConfigReader FILE_CR = new FileConfigReader();
    private static final ConfigReader ENV_VAR_CR = new EnvVarConfigReader();

    private ConfigImp(){}

    public static Config init(){
        String envVarFilePath = System.getenv(CONFIG_LOCATION_ENV_PATH_KEY);
        ConfigImp _tmpConfig = null;
        if ( envVarFilePath != null && !envVarFilePath.isEmpty()){
            _tmpConfig = (ConfigImp) ENV_VAR_FILE_CR.readConfig();
        }else if (System.getenv().containsKey(APPLICATION_NAME)){
            _tmpConfig = (ConfigImp) ENV_VAR_CR.readConfig();
        }else{
            _tmpConfig = (ConfigImp) FILE_CR.readConfig();
        }
        if (_tmpConfig.getEtcdConfig() == null )
            _tmpConfig.etcdConfig = new EtcdConfig();
        if (_tmpConfig.etcdConfig.getEndpoint() == null || _tmpConfig.etcdConfig.getEndpoint().isEmpty())
            _tmpConfig.etcdConfig.setEndpoint("etcd://localhost:2379");
        if (_tmpConfig.namespace == null || _tmpConfig.namespace.isEmpty())
            _tmpConfig.namespace = DEFAULT_NAMESPACE;
        if (_tmpConfig.set == null || _tmpConfig.set.isEmpty())
            _tmpConfig.namespace = DEFAULT_SET;

        try {
            _tmpConfig.check();
            return _tmpConfig;
        } catch (PanshiException e) {
            throw new IllegalArgumentException(e.formatMessage());
        }
    }

    private EtcdConfig etcdConfig;
    private String namespace;
    private String applicationName;
    private String set;

    @Nonnull
    @Override
    public EtcdConfig getEtcdConfig() {
        return this.etcdConfig;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Nullable
    @Override
    public String getSet() {
        return set;
    }

    @Override
    public void check() throws PanshiException {
        if (etcdConfig == null ||
                etcdConfig.getEndpoint() == null ||
                etcdConfig.getEndpoint().isEmpty()){
            throw PanshiException.newError(ErrorCode.INVALID_INPUT,"etcdConfig require");
        }
        if (applicationName == null || applicationName.isEmpty()){
            throw PanshiException.newError(ErrorCode.INVALID_INPUT,"applicationName require");
        }
    }

    interface ConfigReader {
        Config readConfig() ;

        default Config readConfigFromByteSource(ByteSource byteSource){
            try {
                byte[] configFileBytes = byteSource.read();
                ConfigImp _tmpConfig = MAPPER.readValue(configFileBytes, new TypeReference<ConfigImp>() {
                });
                if (_tmpConfig.namespace == null || _tmpConfig.namespace.isEmpty()){
                    _tmpConfig.namespace = DEFAULT_NAMESPACE;
                }
                if (_tmpConfig.set == null || _tmpConfig.set.isEmpty()){
                    _tmpConfig.set = DEFAULT_SET;
                }
                _tmpConfig.check();
                LOGGER.debug("panshi config init success, config {} ",_tmpConfig);
                return _tmpConfig;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (PanshiException e) {
                throw new IllegalArgumentException(e.formatMessage());
            }
        }
    }

    private static class EnvVarFileConfigReader implements ConfigReader {
        @Override
        public Config readConfig()  {
            String configFilePath = System.getenv(CONFIG_LOCATION_ENV_PATH_KEY);
            if (configFilePath == null || configFilePath.isEmpty()){
                throw new IllegalArgumentException("not found config file from env path");
            }
            return this.readConfigFromByteSource(Files.asByteSource(new File(configFilePath)));
        }
    }

    private static class FileConfigReader implements ConfigReader{
        @Override
        public Config readConfig()   {
            try {
                return this.readConfigFromByteSource(
                        ClassPath.from(Thread.currentThread().getContextClassLoader())
                                .getResources()
                                .stream()
                                .filter(t -> CONFIG_CLASSPATH_1.equals(t.getResourceName()) ||
                                        CONFIG_CLASSPATH_2.equals(t.getResourceName()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("not found panshi.yaml or panshi.yml"))
                                .asByteSource()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class EnvVarConfigReader implements ConfigReader {
        @Override
        public Config readConfig() {
            String etcdHost = System.getenv(ETCD_HOST);
            String etcdPort = System.getenv(ETCD_PORT);
            String etcdNamespace = System.getenv(ETCD_NAMESPACE);
            String etcdUsername = System.getenv(ETCD_USERNAME);
            String etcdPassword = System.getenv(ETCD_PASSWORD);
            String namespace = System.getenv(NAMESPACE);
            String applicationName = System.getenv(APPLICATION_NAME);
            String set = System.getenv(SET);

            if (etcdHost == null || etcdHost.isEmpty()) etcdHost = "localhost";
            if (etcdPort == null || etcdPort.isEmpty()) etcdPort = "2379";

            ConfigImp configImp = new ConfigImp();
            EtcdConfig _etcdConfig = new EtcdConfig();
            _etcdConfig.setEndpoint(String.format("etcd://%s:%s",etcdHost,etcdPort ));
            _etcdConfig.setNamespace(etcdNamespace);
            _etcdConfig.setUser(etcdUsername);
            _etcdConfig.setPassword(etcdPassword);

            configImp.etcdConfig = _etcdConfig;
            configImp.namespace = namespace;
            configImp.applicationName = applicationName;
            configImp.set = set;

            return configImp;
        }
    }
}
