package com.jiamoon.jmcms.modules.admin.controller;

import com.jiamoon.jmcms.common.controller.BaseController;
import com.jiamoon.jmcms.common.entity.DataEntity;
import com.jiamoon.jmcms.common.util.RedisUtils;
import com.jiamoon.jmcms.modules.admin.SysResourceMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 后台首页控制层
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SysResourceMapper sysResourceMapper;

    @RequestMapping("")
    public String index(Model model) {
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("jiamoon", subject.isAuthenticated());
        return "index";
    }

    @ResponseBody
    @RequestMapping("test")
    public Object test() {
        DataEntity baseEntity = new DataEntity();
        baseEntity.setId("uuid");
        baseEntity.setDelFlag(100);
        return baseEntity;
    }

    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("login")
    @ResponseBody
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "登录成功";
    }

    @ResponseBody
    @RequestMapping("redis")
    public Object redis(String key, String value) {
        //redisUtils.set(key,value,10L, TimeUnit.MINUTES);
        List<HashMap<Object, Object>> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("title", "灭世");
        map.put("time", new Date());
        list.add(map);
        map = new HashMap<>();
        map.put("title", "杀戮");
        map.put("time", new Date());
        list.add(map);
        return redisUtils.get(key);
    }

    @ResponseBody
    @RequestMapping("redisListPush")
    public Object redisListPush(String key) {
        //redisUtils.set(key,value,10L, TimeUnit.MINUTES);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("title", "灭世");
        map.put("time", new Date());
        redisUtils.lPush(key, map);
        return redisUtils.lRange(key, 0, -1);
    }

    @ResponseBody
    @RequestMapping("redisMap")
    public Object redisMap(String key, String hashKey, String value) {
        //redisUtils.set(key,value,10L, TimeUnit.MINUTES);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("title", "灭世");
        map.put("time", new Date());
        redisUtils.hashSet(key, hashKey, value);
        Subject subject = SecurityUtils.getSubject();
        return redisUtils.hashGet(key, hashKey);
    }
}
