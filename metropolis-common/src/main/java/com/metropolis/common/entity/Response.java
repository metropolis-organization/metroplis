package com.metropolis.common.entity;


import com.metropolis.common.constants.SysCodeConstants;

import java.util.HashMap;

/**
 * @author Pop
 * @date 2020/2/16 18:30
 */
public class Response extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;
    public static Response OK = new Response(SysCodeConstants.SUCCESS);
    public static Response FAILURE = new Response(SysCodeConstants.FAILURE);
    public static Response USERORPASSWORD_ERRROR = new Response(SysCodeConstants.USERORPASSWORD_ERRROR);
    public static Response TOKEN_VALID_FAILED = new Response(SysCodeConstants.TOKEN_VALID_FAILED);
    public static Response USERNAME_ALREADY_EXISTS = new Response(SysCodeConstants.USERNAME_ALREADY_EXISTS);

    public static final String CODE = "code";
    public static final String MESSAGE = "message";

    public Response(SysCodeConstants constants) {
        put(CODE,constants.getCode());
        put(MESSAGE,constants.getMessage());
    }

    public Response(String code,String message) {
        put(CODE,code);
        put(MESSAGE,message);
    }
}
