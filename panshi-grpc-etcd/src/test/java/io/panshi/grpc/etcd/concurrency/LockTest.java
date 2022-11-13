package io.panshi.grpc.etcd.concurrency;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.etcd.jetcd.options.PutOption;
import io.grpc.stub.StreamObserver;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LockTest {

    private Client client;
    private Lock lockClient;
    private Lease leaseClient;
    private long leaseId;

    @Before
    public void setup() throws ExecutionException, InterruptedException, TimeoutException {
        this.client = Client.builder()
                .endpoints("etcd://localhost:2379")
//                .user(ByteSequence.from("root",StandardCharsets.UTF_8))
//                .password(ByteSequence.from("121101",StandardCharsets.UTF_8))
                .build();
        this.lockClient = client.getLockClient();
        this.leaseClient = client.getLeaseClient();
        applyLeaseId();
    }

    private synchronized void applyLeaseId() throws ExecutionException, InterruptedException, TimeoutException {
        LeaseGrantResponse grantResponse = leaseClient.grant(30, 3, TimeUnit.SECONDS).get(3, TimeUnit.SECONDS);
        System.err.println("grantResponse " + grantResponse.getID());
        leaseId = grantResponse.getID();
        leaseClient.keepAlive(grantResponse.getID(),
                new StreamObserver<LeaseKeepAliveResponse>() {
            @Override public void onNext(LeaseKeepAliveResponse value) {
                System.err.println(String.format("onNext ...... value = " + value.getID()
                        + "ttl = " +
                        value.getTTL()
                ));
            }
            @Override public void onError(Throwable t) {
                System.err.println("出现异常");
                t.printStackTrace();
            }
            @Override public void onCompleted() {
                System.err.println("done");
            }
        });
    }


    @Test
    public void testLockApi() throws ExecutionException, InterruptedException, TimeoutException {
        ByteSequence lockKey = ByteSequence.from("TestKey".getBytes(StandardCharsets.UTF_8));
        CompletableFuture<LockResponse> completableFuture = lockClient.lock(lockKey,
                this.leaseId);
        LockResponse response = completableFuture.get();
        System.err.println(response);
    }

    @Test
    public void testLeaseKeepAliveApi() throws InterruptedException {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            leaseClient.revoke(leaseId);
        }).start();
        TimeUnit.HOURS.sleep(1L);
    }

    @Test
    public void testPutValueWith() throws ExecutionException, InterruptedException, TimeoutException {
        KV kvClient = this.client.getKVClient();
        ByteSequence key = ByteSequence.from("/root".getBytes(StandardCharsets.UTF_8));
        ByteSequence value = ByteSequence.from("value".getBytes(StandardCharsets.UTF_8));
        PutOption option = PutOption.newBuilder().withLeaseId(this.leaseId).build();
        CompletableFuture<PutResponse> future = kvClient.put(key, value, option);
        PutResponse response = future.get(3, TimeUnit.SECONDS);
        System.err.println(response);
    }

    public static void main(String[] args){
        System.err.println(1%0);
    }

}
