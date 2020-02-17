package com.metropolis.common.redis.serialize;

import com.metropolis.common.redis.serialize.exception.RedisDeserializeException;
import com.metropolis.common.redis.serialize.exception.RedisSerializeException;

/**
 * @author Pop
 * @date 2020/2/17 22:25
 */
public interface RedisSerializer<T> {

    byte[] serialize(T t) throws RedisSerializeException;

    T deserialize(byte[] bytes) throws RedisDeserializeException;

}
