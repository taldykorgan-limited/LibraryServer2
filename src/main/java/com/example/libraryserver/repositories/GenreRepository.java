package com.example.libraryserver.repositories;

import com.example.libraryserver.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
