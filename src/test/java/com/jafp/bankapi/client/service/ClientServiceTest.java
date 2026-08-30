package com.jafp.bankapi.client.service;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.client.entity.ClientStatus;
import com.jafp.bankapi.client.mapper.ClientMapper;
import com.jafp.bankapi.client.repository.ClientRepository;
import com.jafp.bankapi.common.exception.DuplicateResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {


  @Mock
  private ClientRepository clientRepository;


  @Mock
  private ClientMapper clientMapper;


  @InjectMocks
  private ClientServiceImpl clientService;

  @Test
  void save_ShouldCreateClient_WhenDataIsValid(){

    ClientRequestDTO requestDTO =
            new ClientRequestDTO(
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "12345678",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999, 1, 1)
            );


    when(clientRepository.existsByDni(
            requestDTO.dni()
    ))
            .thenReturn(false);


    when(clientRepository.existsByEmail(
            requestDTO.email()
    ))
            .thenReturn(false);


    when(clientRepository.existsByPhoneNumber(
            requestDTO.phoneNumber()
    ))
            .thenReturn(false);



    Client client = new Client();


    when(clientMapper.toEntity(requestDTO))
            .thenReturn(client);



    when(clientRepository.save(client))
            .thenReturn(client);



    ClientResponseDTO responseDTO =
            new ClientResponseDTO(
                    "12345678",
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999, 1, 1),
                    "ACTIVE"
            );


    when(clientMapper.toResponseDTO(client))
            .thenReturn(responseDTO);



    ClientResponseDTO response =
            clientService.save(requestDTO);



    assertEquals(
            "12345678",
            response.dni()
    );

  }

  @Test
  void save_ShouldThrowException_WhenDniAlreadyExists(){

    ClientRequestDTO requestDTO =
            new ClientRequestDTO(
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "12345678",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999,1,1)
            );


    when(clientRepository.existsByDni(
            requestDTO.dni()
    ))
            .thenReturn(true);



    assertThrows(
            DuplicateResourceException.class,
            () -> clientService.save(requestDTO)
    );


    verify(clientRepository, never())
            .save(any(Client.class));

  }

  @Test
  void save_ShouldThrowException_WhenEmailAlreadyExists(){

    ClientRequestDTO requestDTO =
            new ClientRequestDTO(
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "12345678",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999,1,1)
            );


    when(clientRepository.existsByDni(
            requestDTO.dni()
    ))
            .thenReturn(false);


    when(clientRepository.existsByEmail(
            requestDTO.email()
    ))
            .thenReturn(true);



    assertThrows(
            DuplicateResourceException.class,
            () -> clientService.save(requestDTO)
    );


    verify(clientRepository, never())
            .save(any(Client.class));

  }

  @Test
  void save_ShouldThrowException_WhenPhoneNumberAlreadyExists(){

    ClientRequestDTO requestDTO =
            new ClientRequestDTO(
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "12345678",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999,1,1)
            );


    when(clientRepository.existsByDni(
            requestDTO.dni()
    ))
            .thenReturn(false);


    when(clientRepository.existsByEmail(
            requestDTO.email()
    ))
            .thenReturn(false);


    when(clientRepository.existsByPhoneNumber(
            requestDTO.phoneNumber()
    ))
            .thenReturn(true);



    assertThrows(
            DuplicateResourceException.class,
            () -> clientService.save(requestDTO)
    );


    verify(clientRepository, never())
            .save(any(Client.class));

  }

  @Test
  void findById_ShouldReturnClient_WhenClientExists(){

    Client client = new Client();

    client.setId(1L);
    client.setDni("12345678");
    client.setFirstName("Joel");
    client.setStatus(ClientStatus.ACTIVE);



    when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));



    ClientResponseDTO responseDTO =
            new ClientResponseDTO(
                    "12345678",
                    "Joel",
                    "Anderson",
                    "Fernandez",
                    "Pancorvo",
                    "joel@email.com",
                    "999999999",
                    "Lima",
                    LocalDate.of(1999,1,1),
                    "ACTIVE"
            );



    when(clientMapper.toResponseDTO(client))
            .thenReturn(responseDTO);



    ClientResponseDTO response =
            clientService.findById(1L);



    assertEquals(
            "12345678",
            response.dni()
    );

  }
}