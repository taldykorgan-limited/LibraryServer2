package com.example.libraryserver.responses.genres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetGenresResponse {
    private List<GetGenreResponse> genres;
}
