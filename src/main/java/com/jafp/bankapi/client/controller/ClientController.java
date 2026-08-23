package com.jafp.bankapi.client.controller;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.dto.ClientUpdateRequestDTO;
import com.jafp.bankapi.client.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<List<ClientResponseDTO>> findAll() {
    return ResponseEntity.ok(clientService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.findById(id));
  }

  @PostMapping
  public ResponseEntity<ClientResponseDTO> save(
          @Valid @RequestBody ClientRequestDTO requestDTO
  ) {

    ClientResponseDTO response = clientService.save(requestDTO);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientResponseDTO> update(
          @PathVariable Long id,
          @Valid @RequestBody ClientUpdateRequestDTO requestDTO
  ){
    return ResponseEntity.ok(clientService.update(id, requestDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
          @PathVariable Long id
  ){

    clientService.delete(id);

    return ResponseEntity
            .noContent()
            .build();
  }
}
