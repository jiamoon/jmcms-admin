package com.jiamoon.jmcms.common.controller;

import org.springframework.beans.factory.annotation.Value;

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
}
