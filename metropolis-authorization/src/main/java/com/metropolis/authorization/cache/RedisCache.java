package com.metropolis.authorization.cache;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.authorization.properties.ShiroProperties;
import com.metropolis.authorization.redis.RedisManager;
import com.metropolis.common.web.dto.SysUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import java.util.Collection;
import java.util.Set;

/**
 * @author Pop
 * @date 2020/2/18 16:51
 */
@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {

    private RedisManager redisManager;

    private static final String CACHE_TIMEOUT_NA = "N/A";
    private static final String OBJECT_ID_FILED = "id";
    private static final String CACHE_PREFIX = "shiro:cache:";
    private boolean nonTimeout = false;
    private long cacheTimeout = 604800;

    public RedisCache(RedisManager redisManager,ShiroProperties shiroProperties) {
        this.redisManager = redisManager;
        if(CACHE_TIMEOUT_NA.equals(shiroProperties.getCacheTimeout())){nonTimeout=true;}
        else{
            try{
                cacheTimeout = Long.parseLong(shiroProperties.getCacheTimeout());
            }catch (Throwable t){
                nonTimeout = true;
            }
        }

    }

    private String getCacheKey(K k){

        if(k == null){ return null;}
        if(k instanceof String){
            return CACHE_PREFIX+k.toString();
        }else if(k instanceof PrincipalCollection){
            return CACHE_PREFIX+getPrimaryPrincipal((PrincipalCollection) k);
        }else if(k instanceof SysUser){
            return CACHE_PREFIX+((SysUser) k).getId();
        }else if(k instanceof SysUserDto){
            return CACHE_PREFIX+((SysUserDto) k).getId();
        }else{
            //对象的其他处理
        }
        return CACHE_PREFIX+k.toString();
    }

    private String getPrimaryPrincipal(PrincipalCollection principalCollection) {
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        if(primaryPrincipal instanceof String){
            return primaryPrincipal.toString();
        }else if(primaryPrincipal instanceof SysUser){
            return ((SysUser)(primaryPrincipal)).getId().toString();
        }else{
            //对象的其他处理
        }
        return primaryPrincipal.toString();
    }

    @Override
    public V get(K k) throws CacheException {
        //得到缓存
        log.debug(" get cacheKey {}",k);
        if(k==null){return null;}
        //计算key
        String cacheKey = getCacheKey(k);
        return (V) this.redisManager.get(cacheKey);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if(k == null){
            log.warn(" put operation, cacheKey is null");
            return v;
        }
        String cacheKey = getCacheKey(k);
        if(nonTimeout){
            redisManager.set(cacheKey,v);
        }else{
            redisManager.set(cacheKey,v,cacheTimeout);
        }
        //放入缓存
        log.debug(" put cacheKey :{} cacheValue :{}",k,v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if(k == null){return null;}
        String cacheKey = getCacheKey(k);
        redisManager.del(cacheKey);
        //这里选择不返回值，可以考虑用lua脚本代替。
        return null;
    }

    @Override
    public void clear() throws CacheException {
        Set<String> keys = (Set<String>) keys();
        redisManager.del((String[])keys.toArray());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisManager.keys(CACHE_PREFIX+"*");
    }

    @Override
    public Collection<V> values() { return redisManager.mget(keys()); }
}
