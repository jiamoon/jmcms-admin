package com.jiamoon.jmcms.modules.admin.controller;

import com.jiamoon.jmcms.common.controller.BaseController;
import com.jiamoon.jmcms.common.entity.AjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录控制层
 */
@Controller
@RequestMapping("${jmcms.adminPath}")
public class LoginController extends BaseController {
    @GetMapping("login")
    public String loginPage(Model model, HttpServletRequest request) {
        return "admin/login";
    }

    @GetMapping("wapLogin")
    public String wapLogin(Model model, HttpServletRequest request) {
        return "admin/wapLogin";
    }

    @PostMapping("login")
    //@RequestMapping("login")
    public String login(Model model, HttpServletRequest request, String username, String password) {
        if (request.getMethod().equalsIgnoreCase("post")) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
            } catch (UnknownAccountException e) {
                model.addAttribute("error", "账号不存在！");
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("error", "账号或密码错误！");
            } catch (LockedAccountException e) {
                model.addAttribute("error", "账号已被锁定！");
            } catch (DisabledAccountException e) {
                model.addAttribute("error", "账号未启用！");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "未知错误！" + e.getMessage());
            }
        }
        return "admin/login";
    }

    @PostMapping("ajaxLogin")
    @ResponseBody
    public Object ajaxLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return AjaxResult.buildSuccess();
        } catch (Exception e) {
            return e.getClass().getName();
        }
        return AjaxResult.buildSuccess();
    }
}
