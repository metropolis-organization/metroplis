package com.metropolis.authorization.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * @author Pop
 * @date 2020/2/17 20:44
 */
@Slf4j
public class ShiroSessionListenerAdapter extends SessionListenerAdapter {
    @Override
    public void onStart(Session session) {
        log.info(" 开启一个全新的会话，会话id : {} , 会话来源 : {}",session.getId(),session.getHost());
    }
}
