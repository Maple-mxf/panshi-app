package io.panshi.discovery.core.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Service;
import io.panshi.discovery.core.api.repo.ServiceRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServiceRepositoryImp extends AbstractRepository implements ServiceRepository {

    protected ServiceRepositoryImp(Config config) throws PanshiException {
        super(config);
    }

    @Override
    public void putService(Service service) throws PanshiException {

    }

    @Override
    public List<Service> getServiceList() {
        return null;
    }

    @Override
    public boolean exist(Service service) {
        KV kvClient = this.getRepoClient().getKVClient();
        ByteSequence key = ByteSequence.from(
                String.format("%s/%s/%s", ROOT_PATH,service.getNamespace(),service.getName())
                , StandardCharsets.UTF_8);
        GetOption option =
                GetOption.newBuilder()
                        .withLimit(1).isPrefix(false).build();
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
