package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.dtos.LoanDTO;
import jakarta.persistence.MappedSuperclass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDTO loanEntityToLoanDTO(LoanEntity loanEntity);

    LoanEntity loanDTOToLoanEntity(LoanDTO loanDTO);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Named("loanEntityToLoanDTOWithoutUser")
    LoanDTO loanEntityToLoanDTOWithoutUser(LoanEntity loanEntity);

//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "book", ignore = true)
//    @Named("loanEntityListToLoanDTOListWithoutUser")
//    List<LoanDTO> loanEntityListToLoanDTOListWithoutUser(List<LoanEntity> loanEntities);
}
