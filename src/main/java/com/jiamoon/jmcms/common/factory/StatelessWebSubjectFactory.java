package com.jiamoon.jmcms.common.factory;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class StatelessWebSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建 session，如果之后调用 Subject.getSession()将抛出 DisabledSessionException 异常
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
