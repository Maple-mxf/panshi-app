package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.ClientBuilder;
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
import io.panshi.grpc.etcd.api.config.EtcdConfig;
import io.panshi.grpc.etcd.api.exception.PanshiException;
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
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractRepository implements Repository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    private final Client client;
    //  一个客户端全局的租约ID，全局唯一
    private final AtomicLong clientGlobalLeaseId = new AtomicLong(0);

    /**
     * https://etcd.io/docs/v3.4/dev-guide/api_reference_v3/#service-auth-etcdserveretcdserverpbrpcproto
     * @param config
     */
    protected AbstractRepository(Config config) {
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

        // 申请租约
        applyLeaseId();
    }

    @Override
    public long getClientGlobalLeaseId() {
        return this.clientGlobalLeaseId.get();
    }

    @Nonnull
    @Override
    public Client getRepoClient() {
        return this.client;
    }

    protected void restoreGlobalLeaseId(){
        this.clientGlobalLeaseId.set(0);
    }

    private synchronized void applyLeaseId() {
        Lease leaseClient = this.client.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> completableFuture = leaseClient.grant(30);
        completableFuture.whenComplete((grantResponse, throwable) -> {
            if (throwable != null) {
                LOGGER.error("apply lease id failed, exception {} ", throwable.getMessage());
                return;
            }
            AbstractRepository.this.clientGlobalLeaseId
                    .compareAndSet(0,grantResponse.getID());
            LOGGER.info("lease id apply success, lease id = {} ttl = {}, next keep alive for lease",
                    grantResponse.getID(), grantResponse.getTTL());

            // 自动无限期续约，直到客户端revoke掉锁
            leaseClient.keepAlive(AbstractRepository.this.clientGlobalLeaseId.get(),
                    new StreamObserver<LeaseKeepAliveResponse>() {
                        @Override public void onNext(LeaseKeepAliveResponse value) {
                            AbstractRepository.this.clientGlobalLeaseId.
                                    compareAndSet(AbstractRepository.this.clientGlobalLeaseId.get(),
                                            value.getID());
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


    @Override
    public String lock(String identifierName,int waitSeconds) throws PanshiException {
        Lock lockClient = this.client.getLockClient();
        ByteSequence identifierBytesName = ByteSequence.from(identifierName.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<LockResponse> future = lockClient
                .lock(identifierBytesName, clientGlobalLeaseId.get());

        LockResponse response;
        try {
            response = future.get(waitSeconds, TimeUnit.SECONDS);
            return  response.getKey().toString(StandardCharsets.UTF_8);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            LOGGER.error("jvm internal error. lock task execute error {} ",e.getMessage());
            throw PanshiException.newError(
                    io.panshi.grpc.etcd.api.exception.ErrorCode.LOCK_FAIL,"");
        }catch (EtcdException e){
            e.printStackTrace();
            LOGGER.error("Etcd exception {} ",e.getMessage());
            if (ErrorCode.NOT_FOUND.equals(e.getErrorCode())){
                LOGGER.error("lease id not found leaseId = {} ", clientGlobalLeaseId);
                applyLeaseId();
                throw PanshiException.newError(
                        io.panshi.grpc.etcd.api.exception.ErrorCode.LOCK_FAIL,
                        "leaseId = {} not found, continue apply lease ");
            }else{
                throw PanshiException.newError(
                        io.panshi.grpc.etcd.api.exception.ErrorCode.LOCK_FAIL,
                        String.format("unknown etcd exception etcd code = %s, message = %s ",
                                e.getErrorCode(),e.getMessage()));
            }
        }
    }

    @Override
    public void unLock(String lockKey, int waitSeconds) throws PanshiException {
        Lock lockClient = this.client.getLockClient();
        ByteSequence lockBytesKey = ByteSequence.from(lockKey.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<UnlockResponse> future = lockClient.unlock(lockBytesKey);
        try {
            UnlockResponse response = future.get(waitSeconds, TimeUnit.SECONDS);
            LOGGER.info("unlock success, response = {} ",response);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            LOGGER.error("jvm internal error. lock task execute error {} ",e.getMessage());
            throw PanshiException.newError(
                    io.panshi.grpc.etcd.api.exception.ErrorCode.UNLOCK_FAIL,e.getMessage());
        }catch (EtcdException e){
            e.printStackTrace();
            LOGGER.error("Etcd exception {} ",e.getMessage());
        }
    }

    @Override
    public void stop() {
        if (this.getClientGlobalLeaseId() == 0L){
            LOGGER.info("repository stop success");
            this.client.close();
            return;
        }
        Lease leaseClient = this.client.getLeaseClient();
        CompletableFuture<LeaseRevokeResponse> future = leaseClient
                .revoke(this.clientGlobalLeaseId.get());
        future.whenComplete((revokeResponse, throwable) -> {
            if (throwable == null) {
                LOGGER.info("lease revoke success, revoke response {} ", revokeResponse);
                return;
            }
            LOGGER.info("lease revoke error, exception message {} ", throwable.getMessage());
        });
        this.client.close();
        LOGGER.info("repository stop success");
    }
}
