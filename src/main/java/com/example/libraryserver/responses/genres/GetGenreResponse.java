package com.example.libraryserver.responses.genres;

import com.example.libraryserver.entities.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetGenreResponse {
    private Long id;
    private String name;
    private String info;
    private List<BookEntity> books;
}
