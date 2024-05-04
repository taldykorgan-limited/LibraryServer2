package com.example.libraryserver.mappers;

import com.example.libraryserver.dtos.GenreDTO;
import com.example.libraryserver.entities.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO genreEntityToGenreDTO(GenreEntity genreEntity);
    GenreEntity genreDTOToGenreEntity(GenreDTO genreDTO);
}
