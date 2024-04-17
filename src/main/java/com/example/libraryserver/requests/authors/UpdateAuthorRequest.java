package com.example.libraryserver.requests.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAuthorRequest {
    private Long id;
    private String name;
    private String surname;
    private String info;
}
