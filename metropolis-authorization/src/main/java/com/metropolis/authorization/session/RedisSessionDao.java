package com.metropolis.authorization.session;

import com.metropolis.authorization.redis.RedisManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Pop
 * @date 2020/2/17 20:59
 *
 * Redis 缓存全局会话Server 版本
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId=generateSessionId(session);
        //分配id
        assignSessionId(session,sessionId);
        //进入存储操作
        redisManager.set((String) sessionId,session);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {

        //根据id获取这条session

        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果已经过期就没必要更新
        if(session instanceof ValidatingSession&&
                !((ValidatingSession)session).isValid()){return;}
        //更新操作，redis中 可覆盖，依旧调用创建模式
    }

    @Override
    public void delete(Session session) {
        //删除这条session
    }

    @Override
    public Collection<Session> getActiveSessions() {
        //得到所有依旧存活的session，
        return null;
    }
}
