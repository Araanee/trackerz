package com.trackerz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.ClientRepository;
import com.trackerz.model.Client;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    // Repository injection
    private final ClientRepository clientRepository;

    // Constructor injection
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Create new client
    public Client createClient(Client client) {
        // TODO: add validation
        log.info("Creating new client: {}", client.getName());
        Client savedClient = clientRepository.save(client);
        log.info("Successfully created client with ID: {}", savedClient.getId());
        return savedClient;
    }

    // Get client by id
    public Client getClient(Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    // Get all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get clients by name
    public List<Client> getClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    // Update client
    public Client updateClient(Long id, Client updatedClient) {
        Client existingClient = getClient(id);
        existingClient.setName(updatedClient.getName());
        existingClient.setCity(updatedClient.getCity());
        existingClient.setPhoneNumber(updatedClient.getPhoneNumber());
        return clientRepository.save(existingClient);
    }

    // Delete a client
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

}
