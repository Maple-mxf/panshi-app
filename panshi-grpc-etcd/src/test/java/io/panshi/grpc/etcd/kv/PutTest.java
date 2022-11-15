package io.panshi.grpc.etcd.kv;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Lock;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.grpc.stub.StreamObserver;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PutTest {


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
    public void testPutData() throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();

        CompletableFuture<PutResponse> future = kvClient.put(
                ByteSequence.from("/root_path/sub_path/sub_sub_path1", StandardCharsets.UTF_8),
                ByteSequence.from("sub_sub_path1_data", StandardCharsets.UTF_8)
        );

        PutResponse response = future.get();

        CompletableFuture<PutResponse> future2 = kvClient.put(
                ByteSequence.from("/root_path/sub_path", StandardCharsets.UTF_8),
                ByteSequence.from("sub_path_data", StandardCharsets.UTF_8)
        );
        PutResponse response2 = future2.get();

        System.err.println(response);
        System.err.println(response2);

    }
}
