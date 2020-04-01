package com.metropolis.sso.auth.session;


import com.metropolis.sso.auth.properties.ShiroProperties;
import com.metropolis.sso.auth.redis.RedisManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Pop
 * @date 2020/2/17 20:59
 *
 * todo 批量处理改成 Lua 脚本
 * Redis 缓存全局会话Server 版本
 */
public class RedisSessionDao extends AbstractSessionDAO {

    public static final String REDIS_SESSION_PREFIX = "shiro:session:";

    private RedisManager redisManager;
    private ShiroProperties shiroProperties;

    private String getSessionKey(Serializable serializable){return REDIS_SESSION_PREFIX+serializable;}

    public RedisSessionDao(RedisManager redisManager, ShiroProperties shiroProperties) {
        this.redisManager = redisManager;
        this.shiroProperties = shiroProperties;
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId=generateSessionId(session);
        //分配id
        assignSessionId(session,sessionId);

        saveSession(session);

        return sessionId;
    }

    private void saveSession(Session session){
        //进入存储操作
        redisManager.set(getSessionKey(session.getId()),session,shiroProperties.getSessionTimeout());

    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        //根据id获取这条session

        return (Session) redisManager.get(getSessionKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果已经过期就没必要更新
        if(session instanceof ValidatingSession&&
                !((ValidatingSession)session).isValid()){return;}
        //更新操作，redis中 可覆盖，依旧调用创建模式
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        //删除这条session
        this.redisManager.del(getSessionKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        //得到所有依旧存活的session，
        Set<String> keys=this.redisManager.keys(REDIS_SESSION_PREFIX+"*");
        List<Session> sessionList = new ArrayList();
        for (String key:keys){
            sessionList.add((Session) this.redisManager.get(key)) ;
        }
        return sessionList;
    }
}
