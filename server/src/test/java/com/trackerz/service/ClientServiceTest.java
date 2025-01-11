package com.trackerz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.trackerz.repository.ClientRepository;
import com.trackerz.model.Client;
import com.trackerz.exception.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void createClient_ShouldReturnSavedClient() {
        // Arrange
        Client client = new Client();
        client.setName("Test Client");
        client.setPhoneNumber("0123456789");

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client savedClient = clientService.createClient(client);

        // Assert
        assertNotNull(savedClient);
        assertEquals("Test Client", savedClient.getName());
        verify(clientRepository).save(client);
    }

    @Test
    void getClient_ShouldReturnClient_WhenClientExists() {
        // Arrange
        Long id = 1L;
        Client client = new Client();
        client.setId(id);
        client.setName("Test Client");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClient(id);

        // Assert
        assertNotNull(foundClient);
        assertEquals(id, foundClient.getId());
        assertEquals("Test Client", foundClient.getName());
    }

    @Test
    void getClient_ShouldThrowException_WhenClientNotFound() {
        // Arrange
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            clientService.getClient(id);
        });
    }
} 