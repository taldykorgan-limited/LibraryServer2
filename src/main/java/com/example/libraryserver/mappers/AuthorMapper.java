package com.example.libraryserver.mappers;

import com.example.libraryserver.dtos.*;
import com.example.libraryserver.entities.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO authorEntityToAuthorDTO(AuthorEntity authorEntity);
    AuthorEntity authorDTOToAuthorEntity(AuthorDTO authorDTO);
//    BookDTO bookEntityToBookDTO(BookEntity bookEntity);
//    BookEntity bookDTOToBookEntity(BookDTO bookDTO);
//    GenreDTO genreEntityToGenreDTO(GenreEntity genreEntity);
//    GenreEntity genreDTOToGenreEntity(GenreDTO genreDTO);
//    LoanDTO loanEntityToLoanDTO(LoanEntity loanEntity);
//    LoanEntity loanDTOToLoanEntity(LoanDTO loanDTO);
//    UserDTO userEntityToUserDTO(UserEntity userEntity);
//    UserEntity userDTOToUserEntity(UserDTO userDTO);
}
