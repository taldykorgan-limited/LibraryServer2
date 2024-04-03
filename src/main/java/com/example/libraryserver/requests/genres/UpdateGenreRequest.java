package com.example.libraryserver.requests.genres;
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
public class UpdateGenreRequest {
    private Long id;
    private String name;
    private String info;
    private List<BookEntity> books;
}
