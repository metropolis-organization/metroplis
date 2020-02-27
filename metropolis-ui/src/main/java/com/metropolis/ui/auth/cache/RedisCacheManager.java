package com.metropolis.ui.auth.cache;

import com.metropolis.ui.auth.properties.ShiroProperties;
import com.metropolis.ui.auth.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pop
 * @date 2020/2/18 16:49
 *
 * Redis 缓存管理器
 * 每个Realm独享一份缓存
 */
public class RedisCacheManager implements CacheManager {

    private final ConcurrentHashMap<String,Cache> caches = new ConcurrentHashMap<>();

    private RedisManager redisManager;
    private ShiroProperties shiroProperties;

    public RedisCacheManager(RedisManager redisManager, ShiroProperties shiroProperties) {
        this.redisManager = redisManager;
        this.shiroProperties = shiroProperties;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        Cache cache=caches.get(name);

        if(Objects.isNull(cache)){
            cache = caches.putIfAbsent(name,new RedisCache(redisManager,shiroProperties));
        }

        return cache;
    }
}
