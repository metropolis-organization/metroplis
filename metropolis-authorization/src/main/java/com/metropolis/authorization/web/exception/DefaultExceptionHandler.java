package com.metropolis.authorization.web.exception;

import com.metropolis.common.constants.SysCodeConstants;
import com.metropolis.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.InvalidSessionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Pop
 * @date 2020/2/18 15:48
 */
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

//    @ExceptionHandler({InvalidSessionException.class})
//    public ModelAndView processInvalidSessionException(InvalidSessionException e) {
//        log.warn(e.getMessage());
//        //
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("error",SysCodeConstants.SESSION_TIMEOUT_FAILED);
//        return modelAndView;
//    }
}
