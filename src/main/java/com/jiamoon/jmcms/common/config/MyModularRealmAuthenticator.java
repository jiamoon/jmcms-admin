package com.jiamoon.jmcms.common.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    public MyModularRealmAuthenticator() {
        super();
        System.out.println("自定义ModularRealmAuthenticator");
        this.setAuthenticationStrategy(new OnlyOneAuthenticatorStrategy());
    }

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        System.out.println("多个reaml?");
        return super.doMultiRealmAuthentication(realms, token);
    }

    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {
        return super.doSingleRealmAuthentication(realm, token);
    }
}
