package com.jiamoon.jmcms.common.filter;

import com.jiamoon.jmcms.common.util.RequestUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class WapFormAuthenticationFilter extends AuthenticatingFilter {
    String loginUrl = "";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String username = "";
        String password = "";
        return super.createToken(username, password, request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //登录页面的请求
        if (this.pathsMatch(loginUrl, request)) {
            System.out.println("自定义登录界面");
            if (isLoginSubmission(request,response)){
                System.out.println("自定义登录界面---点击登录");
            }
        }
        return true;
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = RequestUtils.getBean(request, ShiroFilterFactoryBean.class);
        return this.pathsMatch(shiroFilterFactoryBean.getLoginUrl(), loginUrl)
                && request instanceof HttpServletRequest && WebUtils.toHttp(request).getMethod().equalsIgnoreCase("POST");
    }
}
