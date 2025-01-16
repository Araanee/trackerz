package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.ClientService;
import com.trackerz.model.Client;
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
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // Get a client by id
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    // Get a client by name
    @GetMapping("/name/{name}")
    public List<Client> getClientByName(@PathVariable String name) {
        return clientService.getClientsByName(name);
    }

    // Create a client
    @PostMapping("/create")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    // Update a client
    @PutMapping("update/{id}") 
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    // Delete a client
    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
