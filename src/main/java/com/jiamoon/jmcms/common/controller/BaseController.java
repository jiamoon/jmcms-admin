package com.jiamoon.jmcms.common.controller;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    /**
     * 后台根路径
     */
    @Value("${jmcms.adminPath}")
    String adminPath;
    /**
     * 产品名称
     */
    @Value("${jmcms.productName}")
    String productName;

    /**
     * 登录认证异常
     */
    @ResponseBody
    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
    public Object authenticationException(HttpServletRequest request, HttpServletResponse response) {
        // 输出JSON
        Map<String,Object> map = new HashMap<>();
        map.put("code", "-999");
        map.put("message", "未登录");
        return map;
    }

    /**
     * 权限异常
     */
    @ResponseBody
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public Object authorizationException(HttpServletRequest request, HttpServletResponse response) {
        // 输出JSON
        Map<String,Object> map = new HashMap<>();
        map.put("code", "-998");
        map.put("message", "无权限");
        return map;
    }
}
