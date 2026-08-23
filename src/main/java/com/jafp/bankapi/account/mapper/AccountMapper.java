package com.jafp.bankapi.account.mapper;

import com.jafp.bankapi.account.dto.AccountResponseDTO;
import com.jafp.bankapi.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(
          target = "clientId",
          source = "client.id"
  )
  @Mapping(
          target = "clientName",
          expression = "java(account.getClient().getFirstName() + \" \" + account.getClient().getLastName())"
  )
  @Mapping(
          target = "accountType",
          expression = "java(account.getAccountType().name())"
  )
  @Mapping(
          target = "accountStatus",
          expression = "java(account.getStatus().name())"
  )
  AccountResponseDTO toResponseDTO(Account account);

}