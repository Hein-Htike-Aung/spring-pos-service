package com.example.posservice.exception;

import com.example.posservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {SpringPosException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> handleException(SpringPosException e) {

        ApiResponse apiExceptionResponse = ApiResponse.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
