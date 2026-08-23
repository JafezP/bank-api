package com.jafp.bankapi.client.service;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.dto.ClientUpdateRequestDTO;
import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.client.entity.ClientStatus;
import com.jafp.bankapi.client.mapper.ClientMapper;
import com.jafp.bankapi.client.repository.ClientRepository;
import com.jafp.bankapi.common.exception.DuplicateResourceException;
import com.jafp.bankapi.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;

  public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
    this.clientRepository = clientRepository;
    this.clientMapper = clientMapper;
  }

  @Override
  public List<ClientResponseDTO> findAll() {
    return clientRepository.findAll()
            .stream()
            .map(clientMapper::toResponseDTO)
            .toList();
  }

  @Override
  public ClientResponseDTO findById(Long id){

    Client client = clientRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Client not found")
            );

    return clientMapper.toResponseDTO(client);
  }

  @Override
  public ClientResponseDTO save(ClientRequestDTO requestDTO) {
    if (clientRepository.existsByDni(requestDTO.dni())) {
      throw new DuplicateResourceException(
              "DNI already registered"
      );
    }

    if (clientRepository.existsByEmail(requestDTO.email())) {
      throw new DuplicateResourceException(
              "Email already registered"
      );
    }
    if (requestDTO.phoneNumber() != null &&
            clientRepository.existsByPhoneNumber(requestDTO.phoneNumber())) {

      throw new DuplicateResourceException(
              "Phone number already registered"
      );
    }
    Client client = clientMapper.toEntity(requestDTO);
    client.setStatus(ClientStatus.ACTIVE);
    Client savedClient = clientRepository.save(client);
    return clientMapper.toResponseDTO(savedClient);
  }

  @Override
  public ClientResponseDTO update(Long id, ClientUpdateRequestDTO requestDTO) {

    Client client = clientRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Client not found")
            );

    if(requestDTO.email() != null &&
            clientRepository.existsByEmailAndIdNot(
                    requestDTO.email(),
                    id
            )) {
      throw new DuplicateResourceException("Email already registered");
    }

    if (requestDTO.phoneNumber() != null &&
            clientRepository.existsByPhoneNumberAndIdNot(
                    requestDTO.phoneNumber(),
                    id
            )) {
      throw new DuplicateResourceException(
              "Phone number already registered"
      );
    }

    clientMapper.updateEntity(requestDTO, client);
    Client updatedClient = clientRepository.save(client);
    return clientMapper.toResponseDTO(updatedClient);
  }

  @Override
  public void delete(Long id) {

    Client client = clientRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Client not found"
                    )
            );

    client.setStatus(ClientStatus.INACTIVE);

    clientRepository.save(client);
  }
}
