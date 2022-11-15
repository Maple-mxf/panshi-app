package io.panshi.config.provider.model;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConfigKey implements Comparable<ConfigKey> {
    private String group;
    private String set;
    private String key;

    @Override
    public int compareTo(ConfigKey other) {
        int c = group.compareTo(other.group);
        if (c > 0) return c;
        c = set.compareToIgnoreCase(other.set);
        if (c > 0) return c;
        return key.compareTo(other.key);
    }
}
