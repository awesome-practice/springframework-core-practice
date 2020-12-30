package com.practice.springframework.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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
import java.util.Locale;
import java.util.Objects;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Result> handleAll(Exception ex, WebRequest request) {
        log.error("global exception", ex);
        if (ex instanceof BusinessException) {
            Result result = new Result(((BusinessException) ex).getStatus(),toLocale(ex.getMessage()));
            return new ResponseEntity<>(result, ((BusinessException) ex).getStatus());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException realEx = (MethodArgumentTypeMismatchException) ex;
            String error = realEx.getName() + " should be of type " + realEx.getRequiredType().getName();
            HttpStatus status = HttpStatus.BAD_REQUEST;
            Result result = new Result(status, error);
            return new ResponseEntity<Result>(result, status);
        } else if (ex instanceof ConstraintViolationException) {
            List<String> errors = new ArrayList<String>();
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) ex).getConstraintViolations()) {
                errors.add(violation.getRootBeanClass().getName() + " " +
                        violation.getPropertyPath() + ": " + violation.getMessage());
            }
            HttpStatus status = HttpStatus.BAD_REQUEST;
            Result result = new Result(status, errors.toString());
            return new ResponseEntity<Result>(result, status);
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException realEx = (ResponseStatusException) ex;
            Result result = new Result(realEx.getStatus(), realEx.getReason());
            return new ResponseEntity<Result>(result, realEx.getStatus());
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            Result result = new Result(status, "system exception");
            return new ResponseEntity<Result>(result, status);
        }

    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        if (body instanceof Result) {
            return new ResponseEntity<>(body, status);
        } else {
            Result result = new Result(status, ex.getLocalizedMessage());
            return new ResponseEntity<>(result, status);
        }

    }

//    ~~~~~~~~~~~~~~~~~~~~~~


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        String error = ex.getParameterName() + " parameter is missing";

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Result result = new Result(badRequest, error);
        return new ResponseEntity<Object>(result, badRequest);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        Result result = new Result(notFound, error);
        return new ResponseEntity<Object>(result, notFound);
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
        Result result = new Result(methodNotAllowed,
                builder.toString());
        return new ResponseEntity<Object>(result, methodNotAllowed);
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

        Result result = new Result(badRequest, String.join(",", errors));
        return new ResponseEntity<>(result, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("global exception", ex);
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Result result = new Result(badRequest, String.join(",", errors));
        return new ResponseEntity<>(result, status);
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
        Result result = new Result(unsupportedMediaType,
                builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(result, unsupportedMediaType);
    }




}
