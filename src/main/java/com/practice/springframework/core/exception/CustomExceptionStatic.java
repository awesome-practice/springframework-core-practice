package com.practice.springframework.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT,reason = "manual static exception")
public class CustomExceptionStatic extends RuntimeException{

    public CustomExceptionStatic() {
    }

    public CustomExceptionStatic(String message) {
        super(message);
    }

    public CustomExceptionStatic(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptionStatic(Throwable cause) {
        super(cause);
    }

}
