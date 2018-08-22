package com.jiamoon.jmcms.modules.admin.controller;

import com.jiamoon.jmcms.common.controller.BaseController;
import com.jiamoon.jmcms.common.util.RedisUtils;
import com.jiamoon.jmcms.modules.admin.service.IAdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 后台登录控制层
 */
@Controller
@RequestMapping("")
public class IndexController extends BaseController {
    @Autowired
    IAdminService adminService;
    @Autowired
    RedisUtils redisUtils;

    @ResponseBody
    @GetMapping("")
    public Object login() {
        return adminService.findAdminByUsername("admin");
    }

    @ResponseBody
    //@GetMapping("admin")
    @RequestMapping("admin")
    public Object admin(HttpServletRequest request) {
        return adminService.findAdminByUsername("admin");
    }

    @ResponseBody
    @GetMapping("admin/getToken")
    public Object getToken(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
    }

    @ResponseBody
    @GetMapping("admin/getToken2")
    public Object getToken2(String token) {
        System.out.println("嗯哼2?");
        ThreadContext.put("a2","操");
        for (Map.Entry<Object, Object> entry : ThreadContext.getResources().entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= "
                    + entry.getValue());
        }
        return ThreadContext.getResources().size();
    }
}
