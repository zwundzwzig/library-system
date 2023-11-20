package com.system.library.global.exception;

import jakarta.persistence.NoResultException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<?> handleNoResultException(NoResultException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }
}
