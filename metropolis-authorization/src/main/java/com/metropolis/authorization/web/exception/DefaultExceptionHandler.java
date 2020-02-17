package com.metropolis.authorization.web.exception;

import com.metropolis.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Pop
 * @date 2020/2/16 22:06
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public Response processIncorrectCredentialsException(AuthenticationException e) {
        log.warn(e.getMessage());
        return new Response("003XXX",e.getMessage());
    }
}
