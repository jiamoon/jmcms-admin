package com.jiamoon.jmcms.modules.admin.controller;

import com.jiamoon.jmcms.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台首页控制层
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping("")
    public String index() {
        return "index";
    }
}
