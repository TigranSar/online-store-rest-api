package com.online.store.exception;

import com.online.store.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(ResourceNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("Resource not found");
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ErrorResponse> productOutOfStockException(ProductOutOfStockException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("PRODUCT_OUT_OF_STOCK");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(NonUniqueDataException.class)
    public ResponseEntity<ErrorResponse> nonUniqueDataException(NonUniqueDataException nonUniqueDataException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(nonUniqueDataException.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("ENTERED_NON_UNIQUE_DATA");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setMessage("Validation failed");
        errorResponse.setError("INVALID_REQUEST");
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        errorResponse.setValidationErrors(validationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> wrongPasswordException(BadCredentialsException badCredentialsException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Wrong password");
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("WRONG_PASSWORD");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(OrderCannotBeCancelledException.class)
    public ResponseEntity<ErrorResponse> cancelOrderException(OrderCannotBeCancelledException orderCannotBeCancelled){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(orderCannotBeCancelled.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("ORDER_CANNOT_BE_CANCELED");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(OrderStatusChangeNotAllowedException.class)
    public ResponseEntity<ErrorResponse> orderStatusException(OrderStatusChangeNotAllowedException orderStatusException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(orderStatusException.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("ORDER_STATUS_CANNOT_BE_CHANGED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException accessDeniedException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("You don't have permission for this resource");
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError("USER_DO_NOT_HAVE_PERMISSION");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
