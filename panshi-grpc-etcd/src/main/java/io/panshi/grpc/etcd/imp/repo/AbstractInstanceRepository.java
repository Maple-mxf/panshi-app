package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.Client;
import io.etcd.jetcd.Lock;
import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.repo.Repository;

import javax.annotation.Nonnull;

public abstract class AbstractInstanceRepository implements Repository {
    private final Config config;
    private final Client client;

    protected AbstractInstanceRepository(Config config) {
        this.config = config;

        this.client = Client.builder()
//                .authInterceptors()
                .endpoints(config.getEtcdConnectionStrings()).build();
    }

    @Nonnull
    @Override
    public Client getRepoClient() {
        return this.client;
    }

    @Override
    public boolean tryLock(String lockKey, int leaseTime) {

        Lock lockClient = this.client.getLockClient();
        lockClient.lock()

        return false;
    }

    @Override
    public boolean releaseLock(String lockKey) {
        return false;
    }
}
