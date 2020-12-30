package com.practice.springframework.core.exception.model;

import com.practice.springframework.core.validation.IntegerIn;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Man {

    @NotBlank
    private String username;

    @IntegerIn(value = {1, 2, 3})
    private int sex;

    @Positive
    private int age;

    @NotEmpty(message = "{email.notempty}")
    @Email
    private String email;

    @Size(
            min = 5,
            max = 14,
            message = "The author email '${validatedValue}' must be between {min} and {max} characters long"
    )
    private String authorEmail;


    @Size(
            min = 5,
            max = 14,
            message = "{constraints.Size.message}"
    )
    private String authorEmailI18n;

    @Min(
            value = 1,
            message = "{constraints.Min.message}"
    )
    private int testCount;

    @DecimalMin(
            value = "50",
            message = "The code coverage ${formatter.format('%1$.2f', validatedValue)} must be higher than {value}%"
    )
    private double codeCoverage;
}
