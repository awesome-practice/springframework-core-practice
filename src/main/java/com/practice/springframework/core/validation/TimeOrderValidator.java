package com.practice.springframework.core.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Luo Bao Ding
 * @since 12/23/2020
 */
public class TimeOrderValidator implements ConstraintValidator<TimeOrder, Object> {

    private String beforeField;
    private String afterField;

    @Override
    public void initialize(TimeOrder constraintAnnotation) {
        beforeField = constraintAnnotation.beforeField();
        afterField = constraintAnnotation.afterField();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object beforeFieldObj = new BeanWrapperImpl(value).getPropertyValue(beforeField);
        if (beforeFieldObj == null) {
            return true;
        }
        Object afterFieldObj = new BeanWrapperImpl(value).getPropertyValue(afterField);
        if (afterFieldObj == null) {
            return true;
        }
        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(beforeField + " must before " + afterField).addConstraintViolation();
        }

        LocalDate beforeDate = null, afterDate = null;
        if (LocalDate.class.isAssignableFrom(beforeFieldObj.getClass())) {
            beforeDate = (LocalDate) beforeFieldObj;
        }
        if (LocalDate.class.isAssignableFrom(afterFieldObj.getClass())) {
            afterDate = (LocalDate) afterFieldObj;
        }
        if (beforeDate != null && afterDate != null) {
            return beforeDate.isBefore(afterDate);
        }

        LocalDateTime beforeDateTime = null, afterDateTime = null;
        if (LocalDateTime.class.isAssignableFrom(beforeFieldObj.getClass())) {
            beforeDateTime = (LocalDateTime) beforeFieldObj;
        }
        if (LocalDateTime.class.isAssignableFrom(afterFieldObj.getClass())) {
            afterDateTime = (LocalDateTime) afterFieldObj;
        }
        if (beforeDateTime != null && afterDateTime != null) {
            return beforeDateTime.isBefore(afterDateTime);
        }

        return true;
    }
}
