package com.metropolis.sso.auth.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.metropolis.common.string.StringUtils;
import com.metropolis.sso.auth.cache.RedisCacheManager;
import com.metropolis.sso.auth.listener.ShiroSessionListener;
import com.metropolis.sso.auth.properties.ShiroProperties;
import com.metropolis.sso.auth.properties.SsoProperties;
import com.metropolis.sso.auth.realm.SsoRealm;
import com.metropolis.sso.auth.redis.RedisManager;
import com.metropolis.sso.auth.session.RedisSessionDao;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Pop
 * @date 2020/2/16 15:53
 */
@Configuration
/*
这里写外部的配置文件对应的java文件
 */
//@EnableConfigurationProperties()
public class ShiroConfig {

    private static final String ANON = "anon";
    private static final String LOG_OUT = "logout";
    private static final String USER = "user";

    @Bean
    @ConfigurationProperties(prefix = "metropolis.shiro")
    public ShiroProperties shiroProperties(){ return new ShiroProperties(); }

    @Bean
    public DefaultWebSecurityManager securityManager(SsoRealm ssoRealm, RedisSessionDao redisSessionDao,
                                                     RedisCacheManager redisCacheManager, SsoProperties ssoProperties, ShiroProperties shiroProperties){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置Realm
        securityManager.setRealm(ssoRealm);
        //设置session 管理器
        securityManager.setSessionManager(sessionManager(redisSessionDao,ssoProperties,shiroProperties));
        // 设置缓存管理器
        securityManager.setCacheManager(redisCacheManager);

        return securityManager;
    }

    @Bean
    public RedisManager redisManager(@Qualifier("redis4ShiroSessionDAO") RedisTemplate redisTemplate){
        return new RedisManager(redisTemplate);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager,ShiroProperties shiroProperties){
        return new RedisCacheManager(redisManager,shiroProperties);
    }

    @Bean
    public RedisSessionDao redisSessionDao(RedisManager redisManager,ShiroProperties shiroProperties){
        return new RedisSessionDao(redisManager,shiroProperties);
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public DefaultWebSessionManager sessionManager(RedisSessionDao redisSessionDao,SsoProperties ssoProperties,ShiroProperties shiroProperties){

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        List<SessionListener> listeners = new ArrayList<>();
        //增加会话监听
        listeners.add(new ShiroSessionListener(ssoProperties));
//        listeners.add(new ShiroSessionListenerAdapter(ssoProperties,redisManager));
        //会话超时时间配置
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout()*1000);
        //设置监听器
        sessionManager.setSessionListeners(listeners);
        //设置 session 持久化机制
        sessionManager.setSessionDAO(redisSessionDao);
        //不支持 sessionid重写到url
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,ShiroProperties shiroProperties){

         ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置管理器
         shiroFilterFactoryBean.setSecurityManager(securityManager);
        //未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        LinkedHashMap<String,String> filterChainMap = new LinkedHashMap<>();
        //不需要认证的url
        String[] anonUrls = StringUtils.split(shiroProperties.getAnonUrl(),",");
        for (String anonUrl:anonUrls){ filterChainMap.put(anonUrl,ANON); }
        //设置退出拦截器
        filterChainMap.put(shiroProperties.getLogoutUrl(),LOG_OUT);
        //其它访问需要验证
        filterChainMap.put("/**",USER);
        //设置不需要拦截参数
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
