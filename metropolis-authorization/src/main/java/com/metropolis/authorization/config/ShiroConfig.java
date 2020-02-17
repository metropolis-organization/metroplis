package com.metropolis.authorization.config;

import com.metropolis.authorization.properties.ShiroProperties;
import com.metropolis.authorization.realm.UserRealm;
import com.metropolis.common.string.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

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
    public SecurityManager securityManager(UserRealm userRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置Realm
        securityManager.setRealm(userRealm);

        //设置session 管理器
//        securityManager.setSessionManager();
        // 设置缓存管理器
//        securityManager.setCacheManager();
        //设置 cookie 模版
//        securityManager.setRememberMeManager();

        return securityManager;
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



}
