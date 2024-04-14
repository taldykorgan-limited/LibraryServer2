package com.example.libraryserver.requests.books;

import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRequest {
    private long id;
    private String title;
    private String description;
    private int quantity;
    private List<Long> authors;
    private List<Long> genres;
}
