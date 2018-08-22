package com.jiamoon.jmcms.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

public class RequestUtils {

    /**
     * 获取注入的指定bean
     *
     * @param request
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T getBean(ServletRequest request, Class<T> bean) {
        ServletContext servletContext = request.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return applicationContext.getBean(bean);
    }
}
