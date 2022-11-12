package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.common.exception.ErrorCode;
import io.etcd.jetcd.common.exception.EtcdException;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lease.LeaseRevokeResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.lock.UnlockResponse;
import io.grpc.stub.StreamObserver;
import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.repo.Repository;
import io.panshi.grpc.etcd.imp.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

public abstract class AbstractInstanceRepository implements Repository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInstanceRepository.class);

    private final Config config;
    private final Client client;
    //  一个客户端一个租约，全局唯一
    private long leaseID;

    protected AbstractInstanceRepository(Config config) {
        this.config = config;
        this.client = Client.builder()
//                .authInterceptors()
                .endpoints(config.getEtcdConnectionStrings()).build();

        // 申请租约
        applyLeaseId();
    }

    @Nonnull
    @Override
    public Client getRepoClient() {
        return this.client;
    }

    private void applyLeaseId() {
        Lease leaseClient = this.client.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> completableFuture = leaseClient.grant(30);
        completableFuture.whenComplete((grantResponse, throwable) -> {
            if (throwable != null) {
                LOGGER.error("apply lease id failed, exception {} ", throwable.getMessage());
                return;
            }
            AbstractInstanceRepository.this.leaseID = grantResponse.getID();
            LOGGER.info("lease id apply success, lease id = {} ttl = {}, next keep alive for lease",
                    grantResponse.getID(), grantResponse.getTTL());

            // 自动无限期续约，直到客户端自动revoker掉锁
            leaseClient.keepAlive(AbstractInstanceRepository.this.leaseID,
                    new StreamObserver<LeaseKeepAliveResponse>() {
                        @Override public void onNext(LeaseKeepAliveResponse value) {
                            AbstractInstanceRepository.this.leaseID = value.getID();
                            LOGGER.info("lease keep alive success, leaseID = {}, ttl = {}, now = {} ",
                                    value.getID(), value.getTTL(), TimeUtils.getNowTime());
                        }
                        @Override public void onError(Throwable exception) {
                            exception.printStackTrace();
                            LOGGER.error("keep alive for lease exception {} ", exception.getMessage());
                            if (exception instanceof EtcdException){
                                EtcdException etcdException = (EtcdException) exception;
                                if (etcdException.getErrorCode() == ErrorCode.NOT_FOUND) {
                                    LOGGER.error("lease id not found, apply lease id with retry");
                                    applyLeaseId();
                                }
                                return;
                            }
                        }
                        @Override public void onCompleted() {
                            LOGGER.error("leaseID keep alive done. not expected.");
                        }
                    });
        });
    }

    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_concurrency_reference_v3/
     *
     * @param lockKey
     * @param leaseTime
     * @return
     */
    @Override
    public boolean lock(String lockKey, int leaseTime) {
        Lock lockClient = this.client.getLockClient();
        ByteSequence lockName = ByteSequence.from(lockKey.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<LockResponse> future = lockClient.lock(lockName, leaseID);
        try {
            LockResponse response = future.get(3, TimeUnit.SECONDS);
            ByteSequence key = response.getKey();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_concurrency_reference_v3/
     *
     * @param lockKey
     * @return
     */
    @Override
    public boolean unLock(String lockKey) {
        Lock lockClient = this.client.getLockClient();
        ByteSequence lockName = ByteSequence.from(lockKey.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<UnlockResponse> future = lockClient.unlock(lockName);
        try {
            UnlockResponse response = future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        Lease leaseClient = this.client.getLeaseClient();
        CompletableFuture<LeaseRevokeResponse> future = leaseClient.revoke(this.leaseID);
        future.whenComplete((revokeResponse, throwable) -> {
            if (throwable == null) {
                LOGGER.info("lease revoke success, revoke response {} ", revokeResponse);
                return;
            }
            LOGGER.info("lease revoke error, exception message {} ", throwable.getMessage());
        });
        this.client.close();
    }
}
