package com.example.libraryserver.requests.books;

import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.GenreEntity;
import com.example.libraryserver.entities.LoanEntity;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    private String title;
    private String description;
    private int quantity;
    private List<Long> authors;
    private List<Long> genres;

}
