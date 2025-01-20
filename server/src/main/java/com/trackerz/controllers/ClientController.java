package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.ClientService;
import com.trackerz.model.Client;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    // Service injection
    private final ClientService clientService;
    
    // Constructor injection
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Get all clients
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    // Get a client by id
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Client client = clientService.getClient(id);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    // Get a client by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Client>> getClientByName(@PathVariable String name) {
        List<Client> clients = clientService.getClientsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    // Create a client
    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    // Update a client
    @PutMapping("update/{id}") 
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(id, client);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    // Delete a client
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
