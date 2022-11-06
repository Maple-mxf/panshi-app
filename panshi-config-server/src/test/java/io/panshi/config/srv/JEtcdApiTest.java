package io.panshi.config.srv;

import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.GetOption;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JEtcdApiTest {


    private Client client;

    @Before
    public void setup() {
        client = Client.builder()
                .endpoints("etcd://127.0.0.1:2379")
                .build();
    }

    @Test
    public void testPut() throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("/panshi-app/config/group3/set1", StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from("text value", StandardCharsets.UTF_8);
        PutResponse putResponse = kvClient.put(key, value).get();
        System.err.println(putResponse);
    }

    @Test
    public void testGetByPrefix() throws ExecutionException, InterruptedException {
        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("/panshi-app/config", StandardCharsets.UTF_8);


        GetOption option = GetOption.newBuilder()
                .withKeysOnly(true)
                .withLimit(2)
//                .withSortField()
//                .withMaxModRevision(1)
//                .withCountOnly(true)
                .isPrefix(true).build();
        Watch watchClient = client.getWatchClient();

        GetResponse getResponse = kvClient.get(key, option).get();

        List<KeyValue> kvs = getResponse.getKvs();

        for (KeyValue kv : kvs) {
             System.err.println(kv.getKey() + ":" + kv.getValue() + " getModRevision  "+kv.getModRevision() + " getCreateRevision "+
                     kv.getCreateRevision() + " getLease "+kv.getLease() + " getVersion "
             +kv.getVersion() ) ;
        }
    }
}
