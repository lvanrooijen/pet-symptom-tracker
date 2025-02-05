package com.laila.pet_symptom_tracker.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ProblemDetail> badRequestHandler(BadRequestException exception) {
    String message;
    if (exception.getMessage() == null || exception.getMessage().isBlank()) {
      message = "Bad Request";
    } else {
      message = exception.getMessage();
    }

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    return ResponseEntity.badRequest().body(problemDetail);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ProblemDetail> usernameNotFoundHandler(
      UsernameNotFoundException exception) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Username not found");
    return ResponseEntity.badRequest().body(problemDetail);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ProblemDetail> forbiddenHandler(ForbiddenException exception) {
    String message;
    if (exception.getMessage() == null || exception.getMessage().isBlank()) {
      message = "Forbidden";
    } else {
      message = exception.getMessage();
    }

    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
    problemDetail.setTitle("Forbidden");
    problemDetail.setDetail(message);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> notFoundHandler(NotFoundException exception) {
    return ResponseEntity.notFound().build();
  }
}
