package com.metropolis.common.redis.serialize;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author Pop
 * @date 2020/2/18 21:38
 */
public class JsonRedisSerializer implements RedisSerializer<Object> {

    private Charset charset = Charset.forName("utf-8");

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return JSON.toJSONString(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return JSON.parseObject(bytes,null);
    }
}
