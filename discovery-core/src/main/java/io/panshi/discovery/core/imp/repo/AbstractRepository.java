package io.panshi.discovery.core.imp.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.ClientBuilder;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.config.EtcdConfig;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.repo.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public abstract class AbstractRepository implements Repository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    private final Client client;


    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_reference_v3/#service-auth-etcdserveretcdserverpbrpcproto
     * @param config
     */
    protected AbstractRepository(Config config) throws PanshiException {
        EtcdConfig etcdConfig = config.getEtcdConfig();
        ClientBuilder clientBuilder = Client.builder()
                .endpoints(etcdConfig.getEndpoints());
        if (etcdConfig.getUser() != null && !etcdConfig.getUser().isEmpty()){
            clientBuilder.user(ByteSequence.from(
                    etcdConfig.getUser().getBytes(StandardCharsets.UTF_8)));
        }
        if (etcdConfig.getPassword() != null && !etcdConfig.getPassword().isEmpty()){
            clientBuilder.user(ByteSequence.from(
                    etcdConfig.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        this.client = clientBuilder.build();

    }

    @Nonnull
    @Override
    public Client getRepoClient() {
        return this.client;
    }



    @Override
    public void stop() {
        this.client.close();
        LOGGER.info("repository stop success");
    }
}
