package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public BookEntity getBookById(Long id) {
        return bookRepository.findBookEntityById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
    }





}
