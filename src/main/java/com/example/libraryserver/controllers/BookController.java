package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.requests.books.UpdateBookRequest;
import com.example.libraryserver.responses.books.GetBookResponse;
import com.example.libraryserver.responses.books.GetBooksResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody CreateBookRequest createBookRequest){
        return new ResponseEntity<>(bookService.createBook(createBookRequest),HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public GetBooksResponse getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public GetBookResponse getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PatchMapping("/update")
    public InfoResponse updateBook(@RequestBody UpdateBookRequest changeBookRequest){
        return bookService.updateBook(changeBookRequest);
    }

    @DeleteMapping("/delete/{id}")
    public InfoResponse deleteBook(@PathVariable("id") Long id){
        return bookService.deleteBook(id);
    }


}
