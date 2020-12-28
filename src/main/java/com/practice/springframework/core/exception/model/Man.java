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


}
