package io.panshi.config.srv.config;

import lombok.Data;

@Data
public class AccountConfig {
    private String account;
    private String password;
    private Role role;
}
