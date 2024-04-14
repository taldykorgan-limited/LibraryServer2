package com.example.libraryserver.services;

import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.exceptions.DatabaseConnectionException;
import com.example.libraryserver.exceptions.ResourceNotFoundException;
import com.example.libraryserver.repositories.AuthorRepository;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.repositories.GenreRepository;
import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.requests.books.UpdateBookRequest;
import com.example.libraryserver.responses.books.GetBookResponse;
import com.example.libraryserver.responses.books.GetBooksResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public ResponseEntity<InfoResponse> createBook(CreateBookRequest createBookRequest) {
        BookEntity bookEntity = BookEntity.builder()
                .title(createBookRequest.getTitle())
                .description(createBookRequest.getDescription())
                .quantity(createBookRequest.getQuantity())
                .authors(createBookRequest.getAuthors())
                .genres(createBookRequest.getGenres())
                .build();
        try {
            bookRepository.save(bookEntity);
            return new ResponseEntity<>(new InfoResponse("Book created: " + bookEntity), HttpStatus.CREATED);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Genre was not created due to problems connecting to the database");
        }
    }

    public GetBookResponse getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findBookEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));

        GetBookResponse getBookResponse = GetBookResponse.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .quantity(bookEntity.getQuantity())
                .authors(bookEntity.getAuthors())
                .description(bookEntity.getDescription())
                .genres(bookEntity.getGenres())
                .build();
        return getBookResponse;
    }

    public GetBooksResponse getAllBooks() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<GetBookResponse> getBookResponseList = bookEntityList.stream()
                .map(bookEntity -> GetBookResponse.builder()
                        .id(bookEntity.getId())
                        .title(bookEntity.getTitle())
                        .quantity(bookEntity.getQuantity())
                        .authors(bookEntity.getAuthors())
                        .description(bookEntity.getDescription())
                        .genres(bookEntity.getGenres())
                        .build())
                .toList();
        return new GetBooksResponse(getBookResponseList);
    }

    public ResponseEntity<InfoResponse> updateBook(UpdateBookRequest updateBookRequest) {
        BookEntity bookEntity = bookRepository.findBookEntityById(updateBookRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + updateBookRequest.getId() + " not found"));
//        try {
            bookEntity.setTitle(updateBookRequest.getTitle());
            bookEntity.setQuantity(updateBookRequest.getQuantity());
            bookEntity.setDescription(updateBookRequest.getDescription());
            //bookEntity.setAuthors(authorRepository.findAllById(updateBookRequest.getAuthors()));
            bookEntity.setGenres(genreRepository.findAllById(updateBookRequest.getGenres()));
        System.out.println(bookEntity.getGenres());
        System.out.println(bookEntity);
            bookRepository.save(bookEntity);
            return new ResponseEntity<>(new InfoResponse("Book with id " + updateBookRequest.getId() + " updated."), HttpStatus.OK);
//        } catch (DataAccessException | PersistenceException e) {
//            throw new DatabaseConnectionException("Book was not updated due to problems connecting to the database");
//        }
    }

    public ResponseEntity<InfoResponse> deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(new InfoResponse("Book with id " + id + " has been deleted."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Book was not deleted due to problems connecting to the database");
        }
    }


}
