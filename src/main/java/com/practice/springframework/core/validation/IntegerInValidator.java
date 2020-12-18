package com.practice.springframework.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IntegerInValidator implements ConstraintValidator<IntegerIn, Integer> {

    private int[] enums;

    @Override
    public void initialize(IntegerIn constraintAnnotation) {
        enums = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (enums == null) {
            return true;
        }
        boolean valid = false;
        for (int ele : enums) {
            if (ele == value) {
                valid = true;
                break;
            }
        }
        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(" must in " + Arrays.toString(enums)).addConstraintViolation();
        }
        return valid;
    }
}