package io.panshi.discovery.core.imp.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.GetOption;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Namespace;
import io.panshi.discovery.core.api.repo.NamespaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * NamespaceRepositoryImp
 */
public class NamespaceRepositoryImp extends AbstractRepository implements NamespaceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(NamespaceRepositoryImp.class);

    protected NamespaceRepositoryImp(Config config) throws PanshiException {
        super(config);
    }

    @Override
    public List<Namespace> getNamespaceList() {
        return new ArrayList<>();
    }

    @Override
    public void putNamespace(Namespace namespace) throws PanshiException {
        namespace.setCreateTime(LocalDateTime.now());
        if (this.exist(namespace.getName())) {
            return;
        }
        KV kvClient = this.getRepoClient().getKVClient();
        String path = String.format("%s/%s", ROOT_PATH, namespace.getName());


        try {
            // NoApply lease
            CompletableFuture<PutResponse> future = kvClient.put(
                    ByteSequence.from(path, StandardCharsets.UTF_8),
                    ByteSequence.from(MAPPER.writeValueAsString(namespace).getBytes(StandardCharsets.UTF_8))
            );
            PutResponse response = future.get(3, TimeUnit.SECONDS);

        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            LOGGER.error("put response data error {} ",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exist(String namespace) throws PanshiException {
        KV kvClient = this.getRepoClient().getKVClient();
        ByteSequence key = ByteSequence.from(
                String.format("%s/%s", ROOT_PATH,namespace),
                StandardCharsets.UTF_8);
        GetOption option = GetOption.newBuilder().withLimit(1).isPrefix(false).build();
        CompletableFuture<GetResponse> future = kvClient.get(key, option);
        GetResponse response;
        try {
            response = future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        long count = response.getCount();
        return count >= 1;
    }
}
