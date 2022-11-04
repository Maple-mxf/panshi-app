package io.panshi.config.srv.repo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.PutResponse;
import io.panshi.config.srv.model.Config;


import java.nio.charset.StandardCharsets;

import static io.panshi.config.srv.config.RepoClient.ETCD_CLIENT;

public class ConfigRepoImp {

    private final String


    public boolean saveConfig() {
        KV kvClient = ETCD_CLIENT.getKVClient();

        ByteSequence key = ByteSequence.from("", StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from("", StandardCharsets.UTF_8);

        try{
            PutResponse putResponse = kvClient.put(key, value).get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public <T> Config<T> queryConfigById(){
        KV kvClient = ETCD_CLIENT.getKVClient();

        kvClient.get()
    }
}
