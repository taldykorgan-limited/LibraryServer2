package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.dtos.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookEntityToBookDTO(BookEntity bookEntity);
    BookEntity bookDTOToBookEntity(BookDTO bookDTO);
}
