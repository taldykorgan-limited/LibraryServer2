package com.example.libraryserver.repositories;

import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.BookEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<BookEntity> findBookEntityById(Long id);

    List<BookEntity> findAllByAuthors(List<AuthorEntity> authors);
    // find bookentyties in range
    List<BookEntity> findAllByIdBetween(Long start, Long end);
    //count all books
}
