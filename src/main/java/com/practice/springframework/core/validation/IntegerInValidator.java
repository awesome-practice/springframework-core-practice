package com.practice.springframework.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IntegerInValidator implements ConstraintValidator<IntegerIn, Integer> {

    private int[] ints;

    @Override
    public void initialize(IntegerIn constraintAnnotation) {
        ints = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (ints == null) {
            return true;
        }
        boolean valid = false;
        for (int ele : ints) {
            if (ele == value) {
                valid = true;
                break;
            }
        }
        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(" must in " + Arrays.toString(ints)).addConstraintViolation();
        }
        return valid;
    }
}