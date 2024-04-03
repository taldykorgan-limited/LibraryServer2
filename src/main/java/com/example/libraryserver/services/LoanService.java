package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.entities.UserEntity;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.repositories.LoanRepository;
import com.example.libraryserver.repositories.UserRepository;
import com.example.libraryserver.requests.loans.CreateLoanRequest;
import com.example.libraryserver.requests.loans.UpdateLoanStatusRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.responses.loans.GetLoanResponse;
import com.example.libraryserver.responses.loans.GetLoansResponse;
import lombok.RequiredArgsConstructor;
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

    public InfoResponse createLoan(CreateLoanRequest createLoanRequest) {
        BookEntity book = bookRepository.findBookEntityById(createLoanRequest.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Book with id " + createLoanRequest.getBookId() + " not found"));
        if (book.getQuantity() > 0){
            LoanEntity loanEntity = LoanEntity.builder()
                    .loanDate(LocalDateTime.now())
                    .status(1)
                    .book(book)
                    .user(createLoanRequest.getUser())
                    .build();
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            loanRepository.save(loanEntity);
            return new InfoResponse("Loan created: " + loanEntity);
        }
        else return new InfoResponse("Book " + book.getTitle() + " is not available");
    }

    public GetLoanResponse getLoanById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loan with id " + id + " not found"));
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
        }
        else throw new NoSuchElementException("User with id " + userId + " not found");

    }

    public InfoResponse updateLoanStatus(UpdateLoanStatusRequest updateLoanStatusRequest) {
        LoanEntity loanEntity = loanRepository.findById(updateLoanStatusRequest.getId())
                .orElseThrow(() -> new NoSuchElementException("Loan with id " + updateLoanStatusRequest.getId() + " not found"));
        if (updateLoanStatusRequest.getStatus() == 2){
            loanEntity.setStatus(updateLoanStatusRequest.getStatus());
            BookEntity book = loanEntity.getBook();
            book.setQuantity(loanEntity.getBook().getQuantity() - 1);
            bookRepository.save(book);
            loanRepository.save(loanEntity);
            return new InfoResponse("Loan has been updated" + loanEntity);
        }
        else throw new IllegalArgumentException("Invalid status");
    }

}
