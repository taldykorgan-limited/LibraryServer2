package com.example.libraryserver.exceptions;

import com.example.libraryserver.responses.general.InfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<InfoResponse> catchResourceNotFoundException(ResourceNotFoundException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new InfoResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatusConflictException.class)
    public ResponseEntity<InfoResponse> catchStatusConflictException(StatusConflictException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new InfoResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<InfoResponse> catchDatabaseConnectionException(DatabaseConnectionException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new InfoResponse(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(OptimisticEntityLockException.class)
    public ResponseEntity<InfoResponse> catchOptimisticEntityLockException(OptimisticEntityLockException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new InfoResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
}
