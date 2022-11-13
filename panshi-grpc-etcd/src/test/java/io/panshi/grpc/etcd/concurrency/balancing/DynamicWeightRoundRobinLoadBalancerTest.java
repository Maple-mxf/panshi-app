package io.panshi.grpc.etcd.concurrency.balancing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.panshi.grpc.etcd.api.model.Instance;
import io.panshi.grpc.etcd.imp.balancing.DynamicWeightRoundRobinLoadBalancer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DynamicWeightRoundRobinLoadBalancerTest {

    private final List<Instance> instanceList = new ArrayList<>();
    private final DynamicWeightRoundRobinLoadBalancer balancer = new DynamicWeightRoundRobinLoadBalancer();

    @Before
    public void setup(){
        instanceList.add(createInstance("L10",10));
        instanceList.add(createInstance("L9",9));
        instanceList.add(createInstance("L8",8));
        instanceList.add(createInstance("L7",7));
        instanceList.add(createInstance("L6",6));
        instanceList.add(createInstance("L5",5));
        instanceList.add(createInstance("L4",4));
        instanceList.add(createInstance("L3",3));
        instanceList.add(createInstance("L2",2));
        instanceList.add(createInstance("L1",1));
    }

    private Instance createInstance(String ip,long weight){
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setWeight(weight);
        return instance;
    }

    @Test
    public void testSelectOneInstance() throws JsonProcessingException {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 55; i++) {
            Instance instance = balancer.selectOneInstance(instanceList);
            list.add(instance.getIp());
        }
        System.err.println(new ObjectMapper().writeValueAsString(list));
    }
}
