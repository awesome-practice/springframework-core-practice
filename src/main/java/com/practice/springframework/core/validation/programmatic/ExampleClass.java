package com.practice.springframework.core.validation.programmatic;

import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@ValidateAnnotation(ExampleClass.ExampleClassValidator.class)
public class ExampleClass {
    private String pilotId;
    private String firstName;

    private LocalTime startTime;
    private LocalDate orderDate;
    private BigDecimal orderPrice;

    public static class ExampleClassValidator implements Validator {
        @Override
        public boolean supports(Class<?> aClass) {
            return ExampleClass.class.isAssignableFrom(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            ExampleClass bean=(ExampleClass)o;
            if (bean.getStartTime() == null ) {
                errors.reject("startTime.null", "startTime should not be null");
            }
            if (bean.getPilotId() == null||bean.getPilotId().isBlank()) {
                errors.reject("pilotId.null", "pilotId should not be null");
            }
            ValidationUtils.rejectIfEmpty(errors, "orderDate", "date.empty",
                    "Date cannot be empty");
            ValidationUtils.rejectIfEmpty(errors, "orderPrice", "price.empty",
                    "Price cannot be empty");
            if (bean.getOrderPrice() != null &&
                    bean.getOrderPrice().compareTo(BigDecimal.ZERO) <= 0) {
                errors.rejectValue("orderPrice", "price.invalid", "Price must be greater than 0");
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");


        }
    }
}
