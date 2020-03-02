package com.metropolis.ui.auth.listener;

import com.metropolis.common.string.StringUtils;
import com.metropolis.ui.auth.redis.RedisManager;
import com.metropolis.ui.auth.session.RedisSessionDao;
import com.metropolis.ui.properties.SsoProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pop
 * @date 2020/2/17 20:44
 */
@Slf4j
public class ShiroSessionListenerAdapter extends SessionListenerAdapter {

    private Set<String> sessionSets = new HashSet();
    private String serviceIp;
    private RedisManager redisManager;
    public ShiroSessionListenerAdapter(SsoProperties ssoProperties,RedisManager redisManager) {
       serviceIp = ssoProperties.getIp();
       this.redisManager = redisManager;
    }

    @Override
    public void onStart(Session session) {
        log.info(" 开启一个全新的会话，会话id : {} , 会话来源 : {}",session.getId(),session.getHost());
    }

    @Override
    public void onStop(Session session) {
        redisManager.del((String[]) sessionSets.toArray());
        log.info(" 删除会话中心会话："+ sessionSets.toArray());
    }
}
