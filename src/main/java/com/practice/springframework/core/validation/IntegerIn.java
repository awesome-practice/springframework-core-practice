package com.practice.springframework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IntegerInValidator.class})
@Documented
public @interface IntegerIn{
    String message() default "{validation.IntegerIn.massage}";  // or without i18n: "number should be in {value}"

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value();

}
