package io.panshi.config.provider.model;

import lombok.Data;

@Data
public class Config implements Comparable<Config> {
    protected String key;
    protected String group;
    protected String set;
    private String content;
    private long version;
    private ConfigValueType configValueType;

    @Override
    public int compareTo(Config other) {
        int c = group.compareTo(other.group);
        if (c > 0) return c;
        c = set.compareToIgnoreCase(other.set);
        if (c > 0) return c;
        c = key.compareTo(other.key);
        if (c > 0) return c;
        return Long.compare(version, other.version);
    }
}
