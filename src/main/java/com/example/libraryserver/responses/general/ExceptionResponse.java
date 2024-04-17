package com.example.libraryserver.responses.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;
}
