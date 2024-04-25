package com.example.libraryserver.repositories;

import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findBookEntityById(Long id);

    List<BookEntity> findAllByAuthors(List<AuthorEntity> authors);
    // find bookentyties in range
    List<BookEntity> findAllByIdBetween(Long start, Long end);
    //count all books
}
