package io.panshi.grpc.discovery;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.discovery.Consumer;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.imp.discovery.ConsumerImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class PanshiNameResolverProvider extends NameResolverProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PanshiNameResolverProvider.class);
    private static final int DEFAULT_PRIORITY = 5;
    private static final String DEFAULT_SCHEME = "etcd";
    private static final String PATTERN = "etcd://[a-zA-Z0-9_:.-]{1,128}";
    private final Config config;
    public PanshiNameResolverProvider(Config config){
        this.config = config;
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }
    @Override
    protected int priority() {
        return DEFAULT_PRIORITY;
    }
    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        if (!DEFAULT_SCHEME.equals(targetUri.getScheme())) {
            return null;
        }
        try{
            Consumer consumer = new ConsumerImp(config);
            return new PanshiNameResolver(targetUri, config, consumer);
        }catch (PanshiException e){
            e.printStackTrace();
            LOGGER.error("create panshi name resolver exception {} ",e.formatMessage());
            throw new IllegalArgumentException(e.formatMessage());
        }
    }
    @Override
    public String getDefaultScheme() {
        return DEFAULT_SCHEME;
    }
}
