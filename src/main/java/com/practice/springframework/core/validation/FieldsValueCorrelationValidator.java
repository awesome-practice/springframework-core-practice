package com.practice.springframework.core.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Luo Bao Ding
 * @since 12/23/2020
 */
public class FieldsValueCorrelationValidator implements ConstraintValidator<FieldsCorrelationNotNull, Object> {
    private String ifFieldValue;
    private String ifField;
    private String[] notNullFields;

    @Override
    public void initialize(FieldsCorrelationNotNull constraintAnnotation) {
        ifFieldValue = constraintAnnotation.ifFieldValue();
        ifField = constraintAnnotation.ifField();
        notNullFields = constraintAnnotation.notNullFields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object ifFieldObj = new BeanWrapperImpl(value).getPropertyValue(ifField);
        if (ifFieldObj != null && ifFieldObj.toString().equals(ifFieldValue)) {
            for (String notNullField : notNullFields) {
                Object notNullFieldObj = new BeanWrapperImpl(value).getPropertyValue(notNullField);
                if (notNullFieldObj == null) {
                    return false;
                }
            }
        }

        return true;
    }
}
