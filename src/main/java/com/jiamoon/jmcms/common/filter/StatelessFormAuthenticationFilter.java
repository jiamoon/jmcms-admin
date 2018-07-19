package com.jiamoon.jmcms.common.filter;

import com.jiamoon.jmcms.common.entity.StatelessToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StatelessFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //判断请求是否是登录请求
        if (this.isLoginRequest(request, response)) {
            //判断请求是否是post方法
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                //如果是get方法则会返回true,跳转到登陆页面
                return true;
            }
        } else {
            //如果访问的是非登录页面，则跳转到登录页面
            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * 是否允许访问，返回true表示允许
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getParameter("token");
        System.out.println("token:" + token);
        //没有token，则代表没有登录
        if (StringUtils.isBlank(token)) {
            System.out.println("没有token,开始执行登录");
            return false;
        } else {
            //有token，则校验token
            if (!"1".equals(token)) {
                //token校验失败
                return false;
            }
            //token校验通过
            return true;
        }
        // return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 保存登录请求，并跳转到
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String loginUrl = this.getLoginUrl();
        WebUtils.issueRedirect(request, response, loginUrl + "?" + httpRequest.getRequestURL());
    }
}
