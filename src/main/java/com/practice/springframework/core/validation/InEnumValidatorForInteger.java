package com.practice.springframework.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Luo Bao Ding
 * @since 12/23/2020
 */
public class InEnumValidatorForInteger implements ConstraintValidator<InEnum, Integer> {

    public static final String ENUM_METHOD_NAME_PARSE_BY = "parseBy";
    @SuppressWarnings("rawtypes")
    private Class<? extends Enum> enumType;

    @Override
    public void initialize(InEnum annotation) {
        enumType = annotation.enumType();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        Method method;
        try {
            method = enumType.getMethod(ENUM_METHOD_NAME_PARSE_BY, Integer.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Object ele;
        try {
            ele = method.invoke(null, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return ele != null;
    }
}
