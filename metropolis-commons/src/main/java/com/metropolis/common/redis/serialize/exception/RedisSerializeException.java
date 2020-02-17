package com.metropolis.common.redis.serialize.exception;

/**
 * @author Pop
 * @date 2020/2/17 22:28
 * 序列化异常
 */
public class RedisSerializeException extends RuntimeException {
    public RedisSerializeException(String message, Throwable cause) {
        super(message, cause);
    }
    public RedisSerializeException(String message) {
        super(message);
    }
}
