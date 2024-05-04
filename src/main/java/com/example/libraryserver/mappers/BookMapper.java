package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.dtos.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LoanMapper.class})
public interface BookMapper {
    /**
     * Maps a BookEntity object to a BookDTO object.
     *
     * @param bookEntity The BookEntity object to be mapped.
     * @return A BookDTO object with fields mapped from the BookEntity object.
     * The 'loans' field in the returned BookDTO object is mapped using the
     * 'loanEntityToLoanDTOWithoutUser' method in the LoanMapper interface.
     * This method ignores the 'user' and 'book' fields in the LoanEntity object,
     * resulting in 'null' values for these fields in the corresponding LoanDTO object.
     *
     * The returned BookDTO object will look like this:
     * {
     *   "id": 1,
     *   "version": 66,
     *   "title": "Carl Treutel",
     *   "description": "Multi-channelled Cotton generating",
     *   "quantity": 100,
     *   "authors": [],
     *   "loans": [
     *     {
     *       "id": 2,
     *       "version": 0,
     *       "loanDate": "2024-05-02T17:03:10.830745",
     *       "status": 1,
     *       "user": null,
     *       "book": null
     *     },
     *     ...
     *   ],
     *   "genres": []
     * }
     */
    @Mapping(target = "loans", qualifiedByName = "loanEntityToLoanDTOWithoutUser")
    BookDTO bookEntityToBookDTOWithoutBookAndUserInLoans(BookEntity bookEntity);
    BookEntity bookDTOToBookEntity(BookDTO bookDTO);
}
