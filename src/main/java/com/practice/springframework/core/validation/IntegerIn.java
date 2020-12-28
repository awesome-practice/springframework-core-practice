package com.practice.springframework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IntegerInValidator.class})
@Documented
public @interface IntegerIn{
    String message() default "{validation.IntegerIn.massage}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value();

}
