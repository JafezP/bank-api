package com.jafp.bankapi.common.exception;

import com.jafp.bankapi.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResource(
          DuplicateResourceException exception,
          HttpServletRequest request
  ) {

    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI(),
            null
            );

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(errorResponse);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(
          ResourceNotFoundException exception,
          HttpServletRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            exception.getMessage(),
            request.getRequestURI(),
            null
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
          MethodArgumentNotValidException exception,
          HttpServletRequest request
  ) {

    Map<String, List<String>> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            Collectors.toList()
                    )
            ));

    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Validation failed",
            request.getRequestURI(),
            errors
    );

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
  }

}