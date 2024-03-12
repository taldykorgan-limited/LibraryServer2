package com.example.libraryserver.repositories;

import com.example.libraryserver.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findBookEntityById(Long id);
}
