package com.example.libraryserver.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class GenreEntity {
    @Id
    @SequenceGenerator(
            name = "genre_sequence",
            sequenceName = "genre_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence"
    )
    private Long id;
    @Version
    private Long version;
    private String name;
    @Column(columnDefinition = "text")
    private String info;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "genres")
    @JsonBackReference(value = "book-genres reference")
    private List<BookEntity> books;
}
