package com.example.libraryserver.exceptions;

public class StatusConflictException extends RuntimeException{
    public StatusConflictException(String message) {
        super(message);
    }
}
