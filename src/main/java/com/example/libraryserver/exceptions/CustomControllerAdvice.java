package com.example.libraryserver.exceptions;
import com.example.libraryserver.responses.general.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(BadRequestProvidedException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadRequestProvidedException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.I_AM_A_TEAPOT);
    }
}
