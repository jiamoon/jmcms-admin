package com.jiamoon.jmcms.common.config;

import com.jiamoon.jmcms.common.factory.StatelessWebSubjectFactory;
import com.jiamoon.jmcms.common.filter.CustomFormAuthenticationFilter;
import com.jiamoon.jmcms.common.filter.WapFormAuthenticationFilter;
import com.jiamoon.jmcms.common.util.RedisSessionDAO;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 过滤器配置
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        // System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
        filters.put("authc", new CustomFormAuthenticationFilter());
        filters.put("wap", new WapFormAuthenticationFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 注意过滤器配置顺序 不能颠倒
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        //        filterChainDefinitionMap.put("/resources/**", "anon");
        filterChainDefinitionMap.put("/admin/logout", "logout");
        filterChainDefinitionMap.put("/test", "anon");
        filterChainDefinitionMap.put("/admin/wapLogin", "wap");
        filterChainDefinitionMap.put("/admin/**", "authc");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin?ok");
        //未授权界面;
        // shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultRealm defaultRealm() {
        DefaultRealm defaultRealm = new DefaultRealm();
        return defaultRealm;
    }

    @Bean
    public TokenRealm tokenRealm() {
        TokenRealm tokenRealm = new TokenRealm();
        return tokenRealm;
    }

    /**
     * 配置安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realmList = new ArrayList<>();
        realmList.add(defaultRealm());
        realmList.add(tokenRealm());
        securityManager.setRealms(realmList);
        //securityManager.setRealm(defaultRealm());
        securityManager.setSubjectFactory(new StatelessWebSubjectFactory());
        //securityManager.setAuthenticator(new OnlyOneAuthenticatorStrategy());
        Authenticator authenticator = securityManager.getAuthenticator();
        System.out.println("安全管理器:" + authenticator.getClass());
        //注入缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        // 自定义session管理 使用redis
        //securityManager.setSessionManager(sessionManager());
        //获取securityManager的SubjectDao的实现类
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        //获取subjectDao的SessionStorageEvaluator的实现类
        DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        //禁用session的存储策略
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return securityManager;
    }

    /**
     * session管理器
     * 第三：需要禁用掉会话调度器，这个主要是由sessionManager进行管理
     *
     * @return
     */
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        //sessionDAO.setCacheManager(ehCacheManager());
        return sessionDAO;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm。<br>
     * FirstSuccessfulStrategy()（只要有一个生效就不会去其它realm验证）。<br>
     * AtLeastOneSuccessfulStrategy，至少有一个生效<br>
     * AllSuccessfulStrategy()（所有realm都验证通过才能成功登陆）
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new MyModularRealmAuthenticator();
        //modularRealmAuthenticator.setRealms(realmList);
        modularRealmAuthenticator.setAuthenticationStrategy(new OnlyOneAuthenticatorStrategy());
        return modularRealmAuthenticator;
    }

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
