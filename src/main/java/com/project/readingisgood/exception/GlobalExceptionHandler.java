package com.project.readingisgood.exception;

import com.project.readingisgood.exception.exceptions.*;
import com.project.readingisgood.result.ErrorResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { CustomerNotFoundException.class })
    public ResponseEntity<ErrorResult> handleCustomerNotFoundException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { BookNotFoundException.class })
    public ResponseEntity<ErrorResult> handleBookNotFoundException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { OrderNotFoundException.class })
    public ResponseEntity<ErrorResult> handleOrderNotFoundException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { QuantityException.class })
    public ResponseEntity<ErrorResult> handleQuantityException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { CustomerAlreadyExistException.class })
    public ResponseEntity<ErrorResult> handleCustomerAlreadyExistException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<ErrorResult> handleBadCredentialsException(Exception ex) {
        return generateResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorResult> generateResponseEntity(String message, HttpStatus httpStatus){
        ErrorResult errorResult = new ErrorResult(message);
        return new ResponseEntity<>(errorResult, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
