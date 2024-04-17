package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.entities.UserEntity;
import com.example.libraryserver.exceptions.DatabaseConnectionException;
import com.example.libraryserver.exceptions.StatusConflictException;
import com.example.libraryserver.exceptions.ResourceNotFoundException;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.repositories.LoanRepository;
import com.example.libraryserver.repositories.UserRepository;
import com.example.libraryserver.requests.loans.CreateLoanRequest;
import com.example.libraryserver.requests.loans.UpdateLoanStatusRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.responses.loans.GetLoanResponse;
import com.example.libraryserver.responses.loans.GetLoansResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ResponseEntity<InfoResponse> createLoan(CreateLoanRequest createLoanRequest) {
        BookEntity book = bookRepository.findBookEntityById(createLoanRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + createLoanRequest.getBookId() + " not found"));
        if (book.getQuantity() > 0) {
            LoanEntity loanEntity = LoanEntity.builder()
                    .loanDate(LocalDateTime.now())
                    .status(1)
                    .book(book)
                    .user(createLoanRequest.getUser())
                    .build();
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            loanRepository.save(loanEntity);
            //return new ResponseEntity<>(HttpStatus.CREATED);
            return new ResponseEntity<>(new InfoResponse("Loan created"), HttpStatus.CREATED);
        }
        //else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book " + book.getTitle() + " is not available");
        else throw new ResourceNotFoundException("Book " + book.getTitle() + " not available");
    }

    public GetLoanResponse getLoanById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan with id " + id + " not found"));
        GetLoanResponse getLoanResponse = GetLoanResponse.builder()
                .id(loanEntity.getId())
                .book(loanEntity.getBook())
                .user(loanEntity.getUser())
                .status(loanEntity.getStatus())
                .loanDate(loanEntity.getLoanDate())
                .build();
        return getLoanResponse;
    }

    public GetLoansResponse getLoansByUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<LoanEntity> loanEntityList = user.get().getLoans();
            List<GetLoanResponse> getLoanResponseList = loanEntityList.stream()
                    .map(loanEntity -> GetLoanResponse.builder()
                            .id(loanEntity.getId())
                            .user(user.get())
                            .status(loanEntity.getStatus())
                            .book(loanEntity.getBook())
                            .loanDate(loanEntity.getLoanDate())
                            .build()

                    ).toList();
            return new GetLoansResponse(getLoanResponseList);
        } else throw new ResourceNotFoundException("User with id " + userId + " not found");
    }

    public ResponseEntity<InfoResponse> updateLoanStatus(UpdateLoanStatusRequest updateLoanStatusRequest) {
        LoanEntity loanEntity = loanRepository.findById(updateLoanStatusRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan with id " + updateLoanStatusRequest.getId() + " not found"));
        if (updateLoanStatusRequest.getStatus() == 2) {
            loanEntity.setStatus(updateLoanStatusRequest.getStatus());
            BookEntity book = loanEntity.getBook();
            book.setQuantity(loanEntity.getBook().getQuantity() - 1);
            try {
                bookRepository.save(book);
                loanRepository.save(loanEntity);
                return new ResponseEntity<>(new InfoResponse("Loan has been updated" + loanEntity), HttpStatus.OK);
            } catch (DataAccessException | PersistenceException e) {
                throw new DatabaseConnectionException("Loan status was not updated due to problems connecting to the database");
            }

            //return new InfoResponse("Loan has been updated" + loanEntity);
        } else throw new StatusConflictException("Invalid status");
    }

}
