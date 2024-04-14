package com.example.libraryserver.responses.loans;
import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.entities.GenreEntity;
import com.example.libraryserver.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLoanResponse {
    private Long id;
    private LocalDateTime loanDate;
    private int status;
    private Book book;
    private UserEntity user;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Book {
        private Long id;
        private String title;
        private String description;
        private int quantity;
        private List<AuthorEntity> authors;
        private List<GenreEntity> genres;
    }
}
