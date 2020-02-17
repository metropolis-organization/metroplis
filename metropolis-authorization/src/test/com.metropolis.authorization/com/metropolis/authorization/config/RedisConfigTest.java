package com.metropolis.authorization.config;

import com.metropolis.authorization.redis.RedisManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Pop
 * @date 2020/2/17 23:49
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisConfigTest {

    @Autowired
    private RedisManager redisManager;

    @Test
    public void redisTemplate() {
        System.out.println(redisManager.get("pop"));
    }
}