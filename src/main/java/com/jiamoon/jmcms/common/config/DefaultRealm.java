package com.jiamoon.jmcms.common.config;

import com.jiamoon.jmcms.common.token.AutoToken;
import com.jiamoon.jmcms.modules.admin.entity.Admin;
import com.jiamoon.jmcms.modules.admin.service.IAdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultRealm extends AuthorizingRealm {
    @Autowired
    IAdminService adminService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 权限授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登陆认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //if (authenticationToken instanceof AutoToken){
        //    AutoToken autoToken = (AutoToken) authenticationToken;
        //    return new SimpleAuthenticationInfo(autoToken, autoToken.getToken(), getName());
        //}
        System.out.println("我是默认realm=开始");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查询
        Admin admin = adminService.findAdminByUsername(token.getPrincipal().toString());
        if ("1".equals(token.getUsername())){
            throw new LockedAccountException();
        }
        else if ("2".equals(token.getUsername())){
            throw new DisabledAccountException();
        }
        else if ("3".equals(token.getUsername())){
            throw new IncorrectCredentialsException();
        }
        //用户不存在
        if (admin == null) {
            throw new UnknownAccountException();
        }
        //用户已被禁用
        else if (admin.getStatus() == -1) {
            throw new LockedAccountException();
        }
        //用户正在审核
        else if (admin.getStatus() == 0) {
            throw new DisabledAccountException();
        }
        System.out.println("我是默认realm=结束");
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), getName());
    }
}
