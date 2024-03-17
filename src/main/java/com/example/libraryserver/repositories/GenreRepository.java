package com.example.libraryserver.repositories;

import com.example.libraryserver.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
