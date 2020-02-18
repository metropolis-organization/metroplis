package com.metropolis.common.encrypt.exception;

/**
 * @program: fire-control-20190911
 * @description: 找不到appid异常
 * @author: Pop
 * @create: 2019-09-19 18:44
 **/
public class UnFoundAppIDException extends RuntimeException {
    public UnFoundAppIDException() { super("AppID 解析不正确！可能原因AppID没找到。"); }
}
