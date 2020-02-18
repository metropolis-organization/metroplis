package com.metropolis.authorization.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.metropolis.authorization.cache.RedisCacheManager;
import com.metropolis.authorization.listener.ShiroSessionListener;
import com.metropolis.authorization.listener.ShiroSessionListenerAdapter;
import com.metropolis.authorization.properties.ShiroProperties;
import com.metropolis.authorization.realm.UserRealm;
import com.metropolis.authorization.redis.RedisManager;
import com.metropolis.authorization.session.RedisSessionDao;
import com.metropolis.common.string.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Pop
 * @date 2020/2/16 15:53
 */
@Configuration
public class ShiroConfig {

    @Autowired
    ShiroProperties shiroProperties;

    private static final String ANON = "anon";
    private static final String LOG_OUT = "logout";
    private static final String USER = "user";

    @Bean
    public SecurityManager securityManager(UserRealm userRealm,RedisSessionDao redisSessionDao,
                                           RedisCacheManager redisCacheManager){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置Realm
        securityManager.setRealm(userRealm);

        //设置session 管理器
        securityManager.setSessionManager(sessionManager(redisSessionDao));
        // 设置缓存管理器
        securityManager.setCacheManager(redisCacheManager);
        //设置 cookie 模版
        securityManager.setRememberMeManager(cookieRememberMeManager());

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
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public DefaultWebSessionManager sessionManager(RedisSessionDao redisSessionDao){

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        List<SessionListener> listeners = new ArrayList<>();
        //增加会话监听
        listeners.add(new ShiroSessionListener());
        listeners.add(new ShiroSessionListenerAdapter());
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

         ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置管理器
         shiroFilterFactoryBean.setSecurityManager(securityManager);
         //登录的Url
         shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
         //登录成功后的Url，验证中心当登录授权成功后，将原有应用的url作为参数并尝试接着访问资源
        //  但是对于验证中心来说，这个成功的路径并不会走
         shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
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

    private SimpleCookie shiroCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(shiroProperties.getCookieTimeout());
        return cookie;
    }

    private CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(shiroCookie());
        byte[] keyBytes = shiroProperties.getSessionEncryptKey().getBytes(Charset.forName("utf-8"));
        String encryptKey = Base64.decodeToString(keyBytes);
        cookieRememberMeManager.setCipherKey(Base64.decode(encryptKey));
        return cookieRememberMeManager;
    }


}
