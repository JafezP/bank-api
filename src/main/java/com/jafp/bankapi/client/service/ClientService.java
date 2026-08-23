package com.jafp.bankapi.client.service;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.dto.ClientUpdateRequestDTO;

import java.util.List;

public interface ClientService {
  List<ClientResponseDTO> findAll();
  ClientResponseDTO findById(Long id);
  ClientResponseDTO save(ClientRequestDTO requestDTO);
  ClientResponseDTO update(Long id, ClientUpdateRequestDTO requestDTO);
  void delete(Long id);
}
