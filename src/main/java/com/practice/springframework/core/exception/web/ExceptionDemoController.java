package com.practice.springframework.core.exception.web;

import com.practice.springframework.core.exception.BusinessException;
import com.practice.springframework.core.exception.StatusBounddException;
import com.practice.springframework.core.exception.model.Man;
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
        throw new StatusBounddException("CustomExceptionStatic");
    }

    @GetMapping("/provided")
    public ResponseEntity<Object> provided() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ResponseStatusException");
    }

    @GetMapping("/providedWithCause")
    public ResponseEntity<Object> providedWithCause() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ResponseStatusException", new RuntimeException("manual case exception"));
    }

    @GetMapping("/dynamic")
    public ResponseEntity<Object> dynamic() {
        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "error.notfound");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok("yes");
    }

    @PostMapping
    public ResponseEntity<Man> post(@RequestBody @Valid Man request) {
        return ResponseEntity.ok(request);
    }
}
