package com.metropolis.common.encrypt;

/**
 * 此接口将用于
 * {@link Aec}
 * 方法体内需要自己实现访问数据的appId与accessKey的数据库映射表，并返回对应的accessKey
 * @see Aec#checkSignature(String, Code, AppDao)
 *
 */
@FunctionalInterface
public interface AppDao {
    String getAccessKeyByAppId(String appId);
}
