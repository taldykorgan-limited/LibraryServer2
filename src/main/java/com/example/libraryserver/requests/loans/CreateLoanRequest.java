package com.example.libraryserver.requests.loans;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanRequest {
    private UserEntity user;
    private Long bookId;
}
