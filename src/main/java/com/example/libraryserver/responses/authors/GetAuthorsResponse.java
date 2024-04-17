package com.example.libraryserver.responses.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthorsResponse {
    private List<GetAuthorResponse> authors;
}
