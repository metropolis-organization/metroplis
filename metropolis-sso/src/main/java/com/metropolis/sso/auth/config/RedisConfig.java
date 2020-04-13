package com.metropolis.sso.auth.config;

import com.metropolis.common.redis.serialize.ObjectRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author Pop
 * @date 2020/2/17 21:48
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import(ShiroImportSelector.class)
@DemoSelector(excludeClass = {TestDemo.class})
public class RedisConfig {

    @Bean(name = "redis4ShiroSessionDAO")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectRedisSerializer objectRedisSerializer = new ObjectRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean(name = "delSessionScript")
    public DefaultRedisScript<Boolean> delSessionScript() {
        return getScript(Boolean.class, "lua/session/delSession.lua");
    }


    private DefaultRedisScript getScript(Class clazz, String url) {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(clazz);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource(url)));
        return script;
    }

}
