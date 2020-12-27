package com.practice.springframework.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT,reason = "manual static exception")
public class StatusBounddException extends RuntimeException{

    public StatusBounddException() {
    }

    public StatusBounddException(String message) {
        super(message);
    }

    public StatusBounddException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusBounddException(Throwable cause) {
        super(cause);
    }

}
