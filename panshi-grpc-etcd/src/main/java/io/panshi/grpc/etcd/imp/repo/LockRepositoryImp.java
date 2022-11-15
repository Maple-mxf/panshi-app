package io.panshi.grpc.etcd.imp.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.common.exception.ErrorCode;
import io.etcd.jetcd.common.exception.EtcdException;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.lock.UnlockResponse;
import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.repo.LockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LockRepositoryImp extends AbstractTempRepository implements LockRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockRepositoryImp.class);

    protected LockRepositoryImp(Config config) throws PanshiException {
        super(config);
    }

    @Override
    public String lock(String identifierName,int waitSeconds) throws PanshiException {
        Lock lockClient = this.getRepoClient().getLockClient();
        ByteSequence identifierBytesName = ByteSequence.from(identifierName.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<LockResponse> future = lockClient
                .lock(identifierBytesName, getLeaseId());

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
                LOGGER.error("lease id not found leaseId = {} ", getLeaseId());
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
        Lock lockClient = this.getRepoClient().getLockClient();
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
}
