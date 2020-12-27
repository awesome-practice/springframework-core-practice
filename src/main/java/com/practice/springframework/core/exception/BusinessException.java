package com.practice.springframework.core.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private HttpStatus status;

    public BusinessException(HttpStatus status) {
        this.status = status;
    }

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public BusinessException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
