package com.jiamoon.jmcms.common.filter;

import com.alibaba.fastjson.JSON;
import com.jiamoon.jmcms.common.token.AutoToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 是否放行
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);
        System.out.println("是否放行URL = " + httpServletRequest.getMethod() + "  " + httpServletRequest.getRequestURL());
        System.out.println("是否放行" + subject.isAuthenticated());
        //是否token请求，或者cookie带有token
        if (isTokenSubmission(request) || cookieHaveToken(request)) {
            //验证token
            //创建令牌
            AutoToken autoToken = new AutoToken("key", "123456");
            try {
                subject = getSubject(request, response);
                subject.login(autoToken);
                System.out.println("自动登录。是否放行" + subject.isAuthenticated());
                System.out.println(HttpServletResponse.SC_UNAUTHORIZED);
                return true;//认证成功，过滤器链继续
            } catch (AuthenticationException e) {
                System.out.println("自定义登录失败");
                e.printStackTrace();
                return false;
                //try {
                //    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                //} catch (IOException e1) {
                //    e1.printStackTrace();
                //}
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
        //转到拒绝访问处理逻辑
        //return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                System.out.println("成功了吗？");
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    /**
     * 拒绝处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (this.isLoginRequest(request, response)) {
            //POST登录请求
            if (this.isLoginSubmission(request, response)) {
                System.out.println("执行登录");
                return this.executeLogin(request, response);
            } else {
                //登录页面，过滤器继续
                return true;
            }
        } else {
            //保存登录之前的请求地址
            String savedRequest = Base64.encodeBase64String(JSON.toJSONString(new SavedRequest(httpServletRequest)).getBytes());
            Cookie cookie = new Cookie("savedRequest", savedRequest);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            //跳转登录界面
            String loginUrl = this.getLoginUrl();
            WebUtils.issueRedirect(request, response, loginUrl + "?redirect_url=" + httpServletRequest.getRequestURL()
                    + (StringUtils.isNotBlank(httpServletRequest.getQueryString()) ? "?" + httpServletRequest.getQueryString() : ""));
        }
        //打住，到此为止
        return false;
    }

    /**
     * 是否是token请求
     *
     * @param request
     * @return
     */
    protected boolean isTokenSubmission(ServletRequest request) {
        String token = request.getParameter("token");
        return (request instanceof HttpServletRequest) && StringUtils.isNotBlank(token);
    }

    /**
     * Cookie是否存储有token
     *
     * @param request
     * @return
     */
    protected boolean cookieHaveToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpServletRequest.getCookies();
        //System.out.println("获取全部cookie   " + cookies.length);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 登录成功
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        this.issueSuccessRedirect(request, response);
        return false;
    }

    /**
     * 登录成功跳转
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String successUrl = null;
        boolean contextRelative = true;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HashMap<String, String> map = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedRequest".equals(cookie.getName())) {
                    map = JSON.parseObject(new String(Base64.decodeBase64(cookie.getValue().getBytes())), HashMap.class);
                    System.out.println("获取登录前的地址cookie=" + JSON.toJSONString(cookie));
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                    break;
                }
            }
        }
        if (map != null && map.get("method").equalsIgnoreCase("GET")) {
            StringBuilder requestUrl = new StringBuilder(map.get("requestURI"));
            if (map.get("queryString") != null) {
                requestUrl.append("?").append(map.get("queryString")).append("&token=hello");
            } else {
                requestUrl.append("?token=hello");
            }
            successUrl = requestUrl.toString();
            contextRelative = false;
        }
        if (successUrl == null) {
            successUrl = this.getSuccessUrl();
        }
        System.out.println("获取登录前的地址successUrl=" + successUrl);
        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the successUrlFallback method parameter. One of these must be non-null for issueSuccessRedirect() to work.");
        } else {
            //保存token
            Cookie cookie = new Cookie("token", "123456");
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            httpServletResponse.addCookie(cookie);
            WebUtils.issueRedirect(request, response, successUrl, (Map) null, contextRelative, true);
        }
    }
}
