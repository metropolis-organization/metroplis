package com.metropolis.sso.auth.listener;

import com.metropolis.common.shiro.Shiros;
import com.metropolis.common.string.StringUtils;
import com.metropolis.sso.auth.properties.SsoProperties;
import com.metropolis.sso.auth.session.RedisSessionDao;
import com.metropolis.sso.auth.session.RedisSessionManager;
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

    private SsoProperties ssoProperties;
    private RedisSessionManager redisSessionManager;
    public ShiroSessionListener(SsoProperties ssoProperties,RedisSessionManager redisSessionManager) {
        this.ssoProperties = ssoProperties;
        this.redisSessionManager =redisSessionManager;
    }
    @Override
    public void onStart(Session session) {
        if(StringUtils.equals(ssoProperties.getIp(),session.getHost())){
//            redisSessionManager.
//                    addSession(Shiros.getCurrentUser(),
//                            ssoProperties.getGroup(),session.getId()+"");
            log.info(" 来自验证中心的会话已添加。");
        }else{

        }

    }

    @Override
    public void onStop(Session session) {

    }

    @Override
    public void onExpiration(Session session) {

    }
}
