package com.practice.springframework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {InEnumValidatorForInteger.class})
@Documented
public @interface InEnum {
    @SuppressWarnings("rawtypes")
    Class<? extends Enum> enumType();

    String message() default "{validation.InEnum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}