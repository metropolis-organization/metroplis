package com.metropolis.authorization.web.exception;

import com.metropolis.authorization.validate.exception.ValidateCodeException;
import com.metropolis.common.constants.SysCodeConstants;
import com.metropolis.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.InvalidSessionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Pop
 * @date 2020/2/16 22:06
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionRestHandler {

    @ExceptionHandler({AuthenticationException.class})
    public Response processIncorrectCredentialsException(AuthenticationException e) {
        log.warn(e.getMessage());
        return new Response("003XXX",e.getMessage());
    }


    @ExceptionHandler({ValidateCodeException.class})
    public Response processValidateCodeException(ValidateCodeException e) {
        log.warn(e.getMessage());
        return new Response("003XXX",e.getMessage());
    }

}
