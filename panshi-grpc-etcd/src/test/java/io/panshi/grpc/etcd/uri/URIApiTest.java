package io.panshi.grpc.etcd.uri;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class URIApiTest {

    @Test
    public void testURIApiBase() throws URISyntaxException {
        URI uri = new URI("zookeeper://zk.example.com:9900/example_service");
        System.err.println(uri);
        System.err.println(uri.getHost());
        System.err.println(uri.getPort());
        System.err.println(uri.getPath());
    }

}
