package com.metropolis.authorization.validate.exception;

/**
 * @author Pop
 * @date 2020/2/19 22:51
 */
public class ValidateCodeException extends RuntimeException {
    public ValidateCodeException(String message) {
        super(message);
    }
    public ValidateCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
