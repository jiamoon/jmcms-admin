package com.jiamoon.jmcms.common.entity;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

public class StatelessToken extends UsernamePasswordToken implements AuthenticationToken {
    public StatelessToken(String username, String password) {
        super(username, password);
    }
}
