package com.jiamoon.jmcms.common.util;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {
    @Autowired
    RedisUtils redisUtils;
    String keyPrefix = "jm_session:";

    @Override
    protected void doUpdate(Session session) {
        System.out.println("更新session " + session.getId().toString());
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        System.out.println("删除session ");
        redisUtils.remove(keyPrefix + session.getId().toString());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        System.out.println("创建session " + sessionId);
        redisUtils.lPush(keyPrefix + sessionId.toString(), session);
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        System.out.println("读取session ");
        return (Session) redisUtils.get(keyPrefix + serializable.toString());
    }
}
