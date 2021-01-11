package com.practice.springframework.core.validation.programmatic;

import org.springframework.validation.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ValidateAnnotation {
    Class<? extends Validator> value();
}