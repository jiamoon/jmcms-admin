package com.jiamoon.jmcms.common.token;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

public class AutoToken implements AuthenticationToken {
    @Setter
    @Getter
    private String tokenKey;
    @Setter
    @Getter
    private String token;

    public AutoToken(String tokenKey, String token) {
        this.tokenKey = tokenKey;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
