package com.example.libraryserver.responses.loans;
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
public class GetLoanResponse {
    private Long id;
    private LocalDateTime loanDate;
    private int status;
    private BookEntity book;
    private UserEntity user;
}
