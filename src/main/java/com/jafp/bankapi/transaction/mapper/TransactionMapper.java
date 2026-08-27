package com.jafp.bankapi.transaction.mapper;

import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  @Mapping(
          target = "accountNumber",
          source = "account.accountNumber"
  )
  @Mapping(
          target = "transactionType",
          expression = "java(transaction.getTransactionType().name())"
  )
  @Mapping(
          target = "status",
          expression = "java(transaction.getStatus().name())"
  )
  TransactionResponseDTO toResponseDTO(Transaction transaction);

}
