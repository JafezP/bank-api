package com.jafp.bankapi.client.mapper;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.dto.ClientUpdateRequestDTO;
import com.jafp.bankapi.client.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  ClientResponseDTO toResponseDTO(Client client);

  Client toEntity(ClientRequestDTO requestDTO);

  void updateEntity(
          ClientUpdateRequestDTO requestDTO,
          @MappingTarget Client client
  );
}
