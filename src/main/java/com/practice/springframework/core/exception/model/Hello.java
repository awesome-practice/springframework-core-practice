package com.practice.springframework.core.exception.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
public class Hello {

    @NotBlank
    private String username;

    @PositiveOrZero
    private int sex;

}
