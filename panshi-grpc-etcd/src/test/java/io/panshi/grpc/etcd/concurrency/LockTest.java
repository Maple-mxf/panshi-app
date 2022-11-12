package io.panshi.grpc.etcd.concurrency;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.lock.LockResponse;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LockTest {

    private Client client;
    private Lock lockClient;
    private Lease leaseClient;

    @Before
    public void setup() {
        this.client = Client.builder().endpoints("etcd://localhost:2379").build();
        this.lockClient = client.getLockClient();
        this.leaseClient = client.getLeaseClient();
    }

    @Test
    public void testLockApi() throws ExecutionException, InterruptedException, TimeoutException {
        LeaseGrantResponse grantResponse = leaseClient.grant(180, 3, TimeUnit.SECONDS).get(3, TimeUnit.SECONDS);
        System.err.println(grantResponse.getID());
        ByteSequence lockKey = ByteSequence.from("TestKey".getBytes(StandardCharsets.UTF_8));
        CompletableFuture<LockResponse> completableFuture = lockClient.lock(lockKey, grantResponse.getID());
        LockResponse response = completableFuture.get();
        System.err.println(response);
    }

    @Test
    public void testLeaseKeepAliveApi() throws ExecutionException, InterruptedException, TimeoutException {
        LeaseGrantResponse grantResponse = leaseClient.grant(3, 3, TimeUnit.SECONDS).get(3, TimeUnit.SECONDS);
        System.err.println("grantResponse " + grantResponse.getID());

        leaseClient.keepAlive(grantResponse.getID(), new StreamObserver<LeaseKeepAliveResponse>() {
            @Override
            public void onNext(LeaseKeepAliveResponse value) {
                System.err.println(String.format("onNext ...... value = " + value.getID()
                        + "ttl = " +
                                value.getTTL()
                        ));
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("出现异常");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.err.println("done");
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                leaseClient.revoke(grantResponse.getID());
            }
        }).start();


        TimeUnit.HOURS.sleep(1L);
    }

}
