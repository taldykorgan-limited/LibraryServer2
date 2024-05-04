package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.dtos.LoanDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDTO loanEntityToLoanDTO(LoanEntity loanEntity);
    LoanEntity loanDTOToLoanEntity(LoanDTO loanDTO);
}
