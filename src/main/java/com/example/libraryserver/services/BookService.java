package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.requests.books.ChangeBookRequest;
import com.example.libraryserver.requests.books.CreateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookEntity getBookById(Long id) {
        return bookRepository.findBookEntityById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found."));
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public String createBook(CreateBookRequest createBookRequest) {
        BookEntity bookEntity = BookEntity.builder()
                .title(createBookRequest.getTitle())
                .description(createBookRequest.getDescription())
                .quantity(createBookRequest.getQuantity())
                .authors(createBookRequest.getAuthors())
                .genres(createBookRequest.getGenres())
                .build();
        bookRepository.save(bookEntity);
        return "Book created: " + bookEntity;
    }

    public String changeBook(ChangeBookRequest changeBookRequest) {
        BookEntity bookEntity = bookRepository.findBookEntityById(changeBookRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("Book with id " + changeBookRequest.getId() + " not found"));
        bookEntity.setTitle(changeBookRequest.getTitle());
        bookEntity.setQuantity(changeBookRequest.getQuantity());
        bookEntity.setDescription(changeBookRequest.getDescription());
        bookEntity.setAuthors(changeBookRequest.getAuthors());
        bookEntity.setGenres(changeBookRequest.getGenres());
        bookRepository.save(bookEntity);
        return "Book with id " + changeBookRequest.getId() + "changed.";
    }

    public String deleteBook(Long id){
        bookRepository.deleteById(id);
        return "Book with id " + id + "has been deleted.";
    }


}
