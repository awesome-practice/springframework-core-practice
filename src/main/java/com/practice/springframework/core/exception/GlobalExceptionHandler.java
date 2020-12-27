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

/**
 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
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

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        String error = ex.getParameterName() + " parameter is missing";

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(badRequest, error);
        return new ResponseEntity<Object>(apiError, badRequest);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiError apiError = new ApiError(notFound, error);
        return new ResponseEntity<Object>(apiError, notFound);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("global exception", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
        ApiError apiError = new ApiError(methodNotAllowed,
                builder.toString());
        return new ResponseEntity<Object>(apiError, methodNotAllowed);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("global exception", ex);
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiError apiError = new ApiError(badRequest, String.join(",", errors));
        return new ResponseEntity<>(apiError, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("global exception", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        HttpStatus unsupportedMediaType = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        ApiError apiError = new ApiError(unsupportedMediaType,
                builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, unsupportedMediaType);
    }


}
