package com.jiamoon.jmcms.common.config;

import com.jiamoon.jmcms.common.entity.StatelessToken;
import com.jiamoon.jmcms.common.token.AutoToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class TokenRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AutoToken;
    }

    /**
     * 权限授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AutoToken autoToken = (AutoToken) authenticationToken;
        System.out.println("自定义realm");
        return new SimpleAuthenticationInfo(autoToken, autoToken.getToken(), getName());
    }
}
