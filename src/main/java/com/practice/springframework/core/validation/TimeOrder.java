package com.practice.springframework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Luo Bao Ding
 * @since 12/23/2020
 */
@Constraint(validatedBy = {TimeOrderValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TimeOrder.List.class)
public @interface TimeOrder {
    String beforeField();

    String afterField();

    String message() default "{validation.TimeOrder.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        TimeOrder[] value();
    }
}
