package io.panshi.discovery.core.imp.repo;

import io.etcd.jetcd.Lease;
import io.etcd.jetcd.common.exception.ErrorCode;
import io.etcd.jetcd.common.exception.EtcdException;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lease.LeaseRevokeResponse;
import io.grpc.stub.StreamObserver;
import io.panshi.discovery.core.api.config.Config;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.repo.TempRepository;
import io.panshi.discovery.core.imp.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

public  abstract class AbstractTempRepository extends AbstractRepository implements TempRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTempRepository.class);
    //  一个客户端全局的租约ID，全局唯一
    private final AtomicLong clientGlobalLeaseId = new AtomicLong(0);

    protected AbstractTempRepository(Config config) throws PanshiException {
        super(config);
        applyLeaseId();
    }

    protected synchronized void applyLeaseId() {
        Lease leaseClient = this.getRepoClient().getLeaseClient();
        CompletableFuture<LeaseGrantResponse> completableFuture = leaseClient.grant(30);
        completableFuture.whenComplete((grantResponse, throwable) -> {
            if (throwable != null) {
                LOGGER.error("apply lease id failed, exception {} ", throwable.getMessage());
                return;
            }
            clientGlobalLeaseId
                    .compareAndSet(0,grantResponse.getID());
            LOGGER.info("lease id apply success, lease id = {} ttl = {}, next keep alive for lease",
                    grantResponse.getID(), grantResponse.getTTL());


        });
    }

    @Override
    public long getLeaseId() {
        return 0;
    }

    @Override
    public void keepAlive() {
        // 自动无限期续约，直到客户端revoke掉锁
        this.getRepoClient().getLeaseClient().keepAlive( clientGlobalLeaseId.get(),
                new StreamObserver<LeaseKeepAliveResponse>() {
                    @Override public void onNext(LeaseKeepAliveResponse value) {
                        clientGlobalLeaseId.compareAndSet(clientGlobalLeaseId.get(),
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
    }

    @Override
    public long getTTL() {
        return 0;
    }

    @Override
    public void stop() {
        if (this.getLeaseId() == 0L){
            LOGGER.info("repository stop success");
            this.getRepoClient().close();
            return;
        }
        Lease leaseClient = this.getRepoClient().getLeaseClient();
        CompletableFuture<LeaseRevokeResponse> future = leaseClient
                .revoke(this.clientGlobalLeaseId.get());
        future.whenComplete((revokeResponse, throwable) -> {
            if (throwable == null) {
                LOGGER.info("lease revoke success, revoke response {} ", revokeResponse);
                return;
            }
            LOGGER.info("lease revoke error, exception message {} ", throwable.getMessage());
        });
    }
}
