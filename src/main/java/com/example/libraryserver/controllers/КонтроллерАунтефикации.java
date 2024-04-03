package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.books.AuthenticationRequest;
import com.example.libraryserver.requests.books.RegisterRequest;
import com.example.libraryserver.responses.books.AuthenticationResponse;
import com.example.libraryserver.services.СервисАунтефикации;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class КонтроллерАунтефикации {
    private final СервисАунтефикации сервисАунтефикации;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(сервисАунтефикации.зарегестрировать(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(сервисАунтефикации.аунтефицировать(request));
    }
}
