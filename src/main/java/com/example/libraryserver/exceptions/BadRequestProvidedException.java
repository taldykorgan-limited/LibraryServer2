package com.example.libraryserver.exceptions;

public class BadRequestProvidedException extends IllegalArgumentException{
    public BadRequestProvidedException(String message) {
        super(message);
    }
}
