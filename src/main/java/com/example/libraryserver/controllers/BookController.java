package com.example.libraryserver.controllers;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.requests.books.ChangeBookRequest;
import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public String createBook(@RequestBody CreateBookRequest createBookRequest){
        return bookService.createBook(createBookRequest);
    }

    @GetMapping("/get")
    public List<BookEntity> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public BookEntity getBookById(@PathVariable("id") Long id){
        return bookService.getBookById(id);
    }

    @PatchMapping("/change")
    public String changeAuthors(@RequestBody ChangeBookRequest changeBookRequest){
        return bookService.changeBook(changeBookRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        return bookService.deleteBook(id);
    }


}
