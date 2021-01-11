package com.practice.springframework.core.validation.programmatic.web;

import com.practice.springframework.core.validation.programmatic.ExampleClass;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

/**
 * @author Luo Bao Ding
 * @since 1/11/2021
 */
@RestController
@RequestMapping("programmaticValidation")
public class ExampleClassController {

    @GetMapping("/noErrParam")
    public @ResponseBody
    Object testMethod(@Valid ExampleClass exampleClass) {
        return Collections.singletonMap("success", true);
    }

    @GetMapping("/haveErrParam")
    public @ResponseBody
    Object noErrParam(@Valid ExampleClass exampleClass, Errors errors) {
        return Collections.singletonMap("success", true);
    }


}
