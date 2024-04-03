package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.requests.books.UpdateBookRequest;
import com.example.libraryserver.responses.books.GetBookResponse;
import com.example.libraryserver.responses.books.GetBooksResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public GetBookResponse getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findBookEntityById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));

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
        if (!bookEntityList.isEmpty()){
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
        else {
            throw new NoSuchElementException("Books not found");
        }
    }

    public InfoResponse createBook(CreateBookRequest createBookRequest) {
        BookEntity bookEntity = BookEntity.builder()
                .title(createBookRequest.getTitle())
                .description(createBookRequest.getDescription())
                .quantity(createBookRequest.getQuantity())
                .authors(createBookRequest.getAuthors())
                .genres(createBookRequest.getGenres())
                .build();
        bookRepository.save(bookEntity);
        return new InfoResponse("Book created: " + bookEntity);
    }

    public InfoResponse updateBook(UpdateBookRequest updateBookRequest) {
        BookEntity bookEntity = bookRepository.findBookEntityById(updateBookRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("Book with id " + updateBookRequest.getId() + " not found"));
        bookEntity.setTitle(updateBookRequest.getTitle());
        bookEntity.setQuantity(updateBookRequest.getQuantity());
        bookEntity.setDescription(updateBookRequest.getDescription());
        bookEntity.setAuthors(updateBookRequest.getAuthors());
        bookEntity.setGenres(updateBookRequest.getGenres());
        bookRepository.save(bookEntity);
        return new InfoResponse("Book with id " + updateBookRequest.getId() + " updated.");
    }

    public InfoResponse deleteBook(Long id){
        bookRepository.deleteById(id);
        return new InfoResponse("Book with id " + id + " has been deleted.");
    }


}
