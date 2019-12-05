package com.belatrixsf.app.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(
      MethodArgumentNotValidException ex) {

    FieldError fieldError = ex.getBindingResult().getFieldError();
    HashMap<String, String> error = new HashMap<>();
    error.put("status", "Bad Request");
    error.put("message", fieldError.getField() + " " + fieldError.getDefaultMessage());
    return ResponseEntity.badRequest().body(error);
  }

}
