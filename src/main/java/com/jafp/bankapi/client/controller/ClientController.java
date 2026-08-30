package com.jafp.bankapi.client.controller;

import com.jafp.bankapi.client.dto.ClientRequestDTO;
import com.jafp.bankapi.client.dto.ClientResponseDTO;
import com.jafp.bankapi.client.dto.ClientUpdateRequestDTO;
import com.jafp.bankapi.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Clients",
        description = "Operations related to clients"
)
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  @Operation(
          summary = "Get all clients",
          description = "Returns all registered clients"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Clients found successfully"
  )
  public ResponseEntity<List<ClientResponseDTO>> findAll() {
    return ResponseEntity.ok(clientService.findAll());
  }

  @GetMapping("/{id}")
  @Operation(
          summary = "Find client by id",
          description = "Search a client using its identifier"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "200",
                  description = "Client found"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Client not found"
          )

  })
  public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.findById(id));
  }

  @PostMapping
  @Operation(
          summary = "Create client",
          description = "Registers a new bank client"
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "201",
                  description = "Client created successfully"
          ),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid request data"
          ),
          @ApiResponse(
                  responseCode = "409",
                  description = "Client already exists"
          )

  })
  public ResponseEntity<ClientResponseDTO> save(
          @Valid @RequestBody ClientRequestDTO requestDTO
  ) {

    ClientResponseDTO response = clientService.save(requestDTO);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
  }

  @PutMapping("/{id}")
  @Operation(
          summary = "Update client",
          description = "Updates client information"
  )
  @ApiResponses({
          @ApiResponse(
                  responseCode = "200",
                  description = "Client updated successfully"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Client not found"
          )
  })
  public ResponseEntity<ClientResponseDTO> update(
          @PathVariable Long id,
          @Valid @RequestBody ClientUpdateRequestDTO requestDTO
  ){
    return ResponseEntity.ok(clientService.update(id, requestDTO));
  }

  @DeleteMapping("/{id}")
  @Operation(
          summary = "Delete client",
          description = "Performs a soft delete on client"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "204",
                  description = "Client deleted successfully"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Client not found"
          )

  })
  public ResponseEntity<Void> delete(
          @PathVariable Long id
  ){

    clientService.delete(id);

    return ResponseEntity
            .noContent()
            .build();
  }
}
