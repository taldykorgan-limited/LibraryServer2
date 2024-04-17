package com.example.libraryserver.entities;

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
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    @Column(columnDefinition = "text")
    private String info;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "authors")
    private List<BookEntity> books;

}
