package io.panshi.config.provider.config;

import lombok.Data;

@Data
public class AccountConfig {
    private String account;
    private String password;
    private Role role;
}
