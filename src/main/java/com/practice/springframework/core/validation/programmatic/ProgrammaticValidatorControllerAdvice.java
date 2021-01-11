package com.practice.springframework.core.validation.programmatic;

import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
public class ProgrammaticValidatorControllerAdvice {
 
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> targetClass = binder.getTarget().getClass();
        if(targetClass.isAnnotationPresent(ValidateAnnotation.class)) {
            ValidateAnnotation annotation = targetClass.getAnnotation(ValidateAnnotation.class);
            Class<? extends Validator> value = annotation.value();
            Validator validator = value.getDeclaredConstructor().newInstance();
            binder.setValidator(validator);
        }
    }
}