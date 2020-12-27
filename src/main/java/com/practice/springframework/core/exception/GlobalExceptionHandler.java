package com.practice.springframework.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest request) {
        log.error("global exception", ex);
        if (ex instanceof BusinessException) {
            ApiError apiError = new ApiError(((BusinessException) ex).getStatus(), ex.getLocalizedMessage());
            return new ResponseEntity<>(apiError, ((BusinessException) ex).getStatus());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException realEx = (MethodArgumentTypeMismatchException) ex;
            String error = realEx.getName() + " should be of type " + realEx.getRequiredType().getName();
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ApiError apiError = new ApiError(status, error);
            return new ResponseEntity<ApiError>(apiError, status);
        } else if (ex instanceof ConstraintViolationException) {
            List<String> errors = new ArrayList<String>();
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) ex).getConstraintViolations()) {
                errors.add(violation.getRootBeanClass().getName() + " " +
                        violation.getPropertyPath() + ": " + violation.getMessage());
            }
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ApiError apiError = new ApiError(status, errors.toString());
            return new ResponseEntity<ApiError>(apiError, status);
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException realEx = (ResponseStatusException) ex;
            ApiError apiError = new ApiError(realEx.getStatus(), realEx.getReason());
            return new ResponseEntity<ApiError>(apiError, realEx.getStatus());
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            ApiError apiError = new ApiError(status, "system exception");
            return new ResponseEntity<ApiError>(apiError, status);
        }

    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        if (body instanceof ApiError) {
            return new ResponseEntity<>(body, status);
        } else {
            ApiError apiError = new ApiError(status, ex.getLocalizedMessage());
            return new ResponseEntity<>(apiError, status);
        }

    }



}
