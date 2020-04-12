package com.metropolis.ui.auth.listener;

import com.metropolis.common.string.StringUtils;
import com.metropolis.sso.auth.properties.SsoProperties;
import com.metropolis.ui.auth.redis.RedisManager;
import com.metropolis.ui.auth.session.RedisSessionDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pop
 * @date 2020/2/17 20:39
 */
@Slf4j
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger();
    public static Set<String> sessionSets = new HashSet();
    private String serviceIp;
    public ShiroSessionListener(SsoProperties ssoProperties) {
        serviceIp = ssoProperties.getIp();
    }
    @Override
    public void onStart(Session session) {
        if(StringUtils.equals(serviceIp,session.getHost())){
            sessionSets.add(RedisSessionDao.REDIS_SESSION_PREFIX+session.getId());
            log.info(" 来自验证中心的会话已添加。");
        }else{
            log.info(" 已有新用户加入，当前会话总数 : {}",sessionCount.incrementAndGet());
            log.info(" 开启一个全新的会话，会话id : {} , 会话来源 : {}",session.getId(),session.getHost());
        }
    }

    @Override
    public void onStop(Session session) {
        log.info(" 已有新用户离开，当前会话总数 : {}",sessionCount.decrementAndGet());
    }

    @Override
    public void onExpiration(Session session) {
        log.info(" 已有会话超时，当前会话总数 : {}",sessionCount.decrementAndGet());
    }
}
