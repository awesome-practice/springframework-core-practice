package com.practice.springframework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Luo Bao Ding
 * @since 12/23/2020
 */
@Constraint(validatedBy = FieldsValueCorrelationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FieldsCorrelationNotNull.List.class)
public @interface FieldsCorrelationNotNull {

    String ifField();

    String ifFieldValue();

    String[] notNullFields() default {};

    String message() default "{validation.FieldsCorrelationNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsCorrelationNotNull[] value();
    }
}
