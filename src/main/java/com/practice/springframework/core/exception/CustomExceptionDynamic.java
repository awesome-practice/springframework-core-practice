package com.practice.springframework.core.exception;

import org.springframework.http.HttpStatus;

public class CustomExceptionDynamic extends RuntimeException{
    private HttpStatus status;

    public CustomExceptionDynamic(HttpStatus status) {
        this.status = status;
    }

    public CustomExceptionDynamic(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public CustomExceptionDynamic(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public CustomExceptionDynamic(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public CustomExceptionDynamic(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
