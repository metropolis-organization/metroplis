package com.metropolis.sso.auth.session;

import com.metropolis.common.web.dto.SysUserDto;
import com.metropolis.sso.auth.properties.ShiroProperties;
import com.metropolis.sso.auth.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-13 14:47
 *                          -> group1  ->  sessionId
 * session:manager:{id}->   -> group2  ->  sessionId
 *                          -> group3  ->  sessionId
 **/
@Service
public class RedisSessionManager extends AbstractSessionManager {

    private static final String SESSION_KEY = "session:manager:";
    @Autowired
    private ShiroProperties shiroProperties;

    @Resource(name = "delSessionScript")
    private DefaultRedisScript delSessionScript;

    @Autowired
    private RedisManager redisManager;

    @Override
    String getKey(SysUserDto sysUser) { return SESSION_KEY+sysUser.getId(); }


    @Override
    public void addSession(SysUserDto sysUser, String group, String sessionId) {
        redisManager.hset(getKey(sysUser),group,sessionId,shiroProperties.getSessionTimeout());
    }

    @Override
    public void delSession(SysUserDto sysUser,String group) {
        //由于用户在直接点击退出登录是，其实也就是注销了其它系统的登录状态，所以这个
        //操作在redis是完全删除管理器中所有session，从原本的map中找到所有用户的key进行删除
        redisManager.execute(delSessionScript,redisManager.setKeys(getKey(sysUser)));
    }
}
