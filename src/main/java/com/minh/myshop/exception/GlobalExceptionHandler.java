package com.minh.myshop.exception;

import com.minh.myshop.dto.ApiResponseDto;
import com.minh.myshop.enums.ResponseStatus;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message("Registration Failed: Please provide valid data.")
                                .response(errorMessage)
                                .build()
                );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> RoleNotFoundExceptionHandler(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = UserIdNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> UserIdNotFoundExceptionHandler(UserIdNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = ProductStockInvalid.class)
    public ResponseEntity<ApiResponseDto<?>> productStockInvalidHandler(ProductStockInvalid exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = NoSuchOrderException.class)
    public ResponseEntity<ApiResponseDto<?>> noSuchElementHandler(NoSuchOrderException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> productNotFoundHandler(ProductNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> notFoundHandler(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(OutOfQuantityInStock.class)
    public ResponseEntity<?> handleOutOfQuantityInStock(OutOfQuantityInStock exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDto.builder()
                                .status(String.valueOf(ResponseStatus.FAIL))
                                .message(exception.getMessage())
                                .build()
                );
    }
}
