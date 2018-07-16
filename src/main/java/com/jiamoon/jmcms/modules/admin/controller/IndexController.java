package com.jiamoon.jmcms.modules.admin.controller;

import com.jiamoon.jmcms.common.controller.BaseController;
import com.jiamoon.jmcms.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("")
    public String index() {
        return "index";
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
    public Object redisMap(String key,String hashKey,String value) {
        //redisUtils.set(key,value,10L, TimeUnit.MINUTES);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("title", "灭世");
        map.put("time", new Date());
        redisUtils.hashSet(key,hashKey,value);
        return redisUtils.hashGet(key,hashKey);
    }
}
