package io.panshi.config.srv.model;

import java.io.Serializable;

public class ServiceInstance implements Serializable,Comparable<ServiceInstance> {
    private String ipAddress;
    private String group;
    private String set;
    private Long time;

    @Override
    public int compareTo(ServiceInstance other) {
        int c = group.compareTo(other.group);
        if (c > 0) return c;
        c = set.compareToIgnoreCase(other.set);
        if (c > 0) return c;
        return ipAddress.compareTo(other.ipAddress);
    }
}
