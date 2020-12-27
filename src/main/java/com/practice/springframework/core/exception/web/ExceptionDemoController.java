package com.practice.springframework.core.exception.web;

import com.practice.springframework.core.exception.CustomExceptionDynamic;
import com.practice.springframework.core.exception.CustomExceptionStatic;
import com.practice.springframework.core.exception.model.Hello;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/exception")
public class ExceptionDemoController {

    @GetMapping("/static")
    public ResponseEntity<Object> getStatic() {
        throw new CustomExceptionStatic("CustomExceptionStatic");
    }

    @GetMapping("/provided")
    public ResponseEntity<Object> provided() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ResponseStatusException");
    }

    @GetMapping("/providedWithCause")
    public ResponseEntity<Object> providedWithCause() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ResponseStatusException", new RuntimeException("manual case exception"));
    }

    @GetMapping("/dynamic")
    public ResponseEntity<Object> dynamic() {
        throw new CustomExceptionDynamic(HttpStatus.BAD_REQUEST, "CustomExceptionDynamic");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok("yes");
    }

    @PostMapping
    public ResponseEntity<Hello> post(@RequestBody @Valid Hello request) {
        return ResponseEntity.ok(request);
    }
}
