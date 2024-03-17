package com.example.libraryserver.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class LoanEntity {
    @Id
    @SequenceGenerator(
            name = "loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    private Long id;
    private LocalDateTime loanDate;
    private int status; // 1- книга на руках у юзера, 2- книга возвращена

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private BookEntity book;

    @PrePersist
    private void init(){
        loanDate = LocalDateTime.now();
    }

}
