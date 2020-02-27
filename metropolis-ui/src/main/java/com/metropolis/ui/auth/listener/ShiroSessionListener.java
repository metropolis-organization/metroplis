package com.metropolis.ui.auth.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pop
 * @date 2020/2/17 20:39
 */
@Slf4j
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger();

    @Override
    public void onStart(Session session) {
        log.info(" 已有新用户加入，当前会话总数 : {}",sessionCount.incrementAndGet());
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
