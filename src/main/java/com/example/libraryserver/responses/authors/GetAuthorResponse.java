package com.example.libraryserver.responses.authors;

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
public class GetAuthorResponse {
    private Long id;
    private String name;
    private String surname;
    private String info;
    private List<BookEntity> books;
}
