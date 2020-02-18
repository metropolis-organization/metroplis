package com.metropolis.common.encrypt.exception;

/**
 * @program: fire-control-20190911
 * @description:
 * @author: Pop
 * @create: 2019-09-19 19:23
 **/
public class UnFoundAccessKeyException extends RuntimeException
{
    public UnFoundAccessKeyException() { super("未知的  AccessKey   异常"); }
}
