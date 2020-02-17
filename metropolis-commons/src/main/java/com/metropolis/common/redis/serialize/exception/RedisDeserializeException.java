package com.metropolis.common.redis.serialize.exception;

/**
 * @author Pop
 * @date 2020/2/17 22:33
 * 反序列化异常
 */
public class RedisDeserializeException extends RuntimeException {
    public RedisDeserializeException(String message) {
        super(message);
    }

    public RedisDeserializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
