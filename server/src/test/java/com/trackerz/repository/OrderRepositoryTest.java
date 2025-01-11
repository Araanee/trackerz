package com.trackerz.repository;

import com.trackerz.model.Order;
import com.trackerz.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldFindOrdersByClient() {
        // Given
        Client client = new Client();
        client.setName("Test Client");
        client.setPhoneNumber("1234567890");
        Client savedClient = clientRepository.save(client);
        assertThat(savedClient.getId()).isNotNull();

        Order order = new Order();
        order.setClient(savedClient);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(100.0);
        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        // When
        List<Order> found = orderRepository.findByClient(savedClient);

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getId()).isNotNull();
        assertThat(found.get(0).getClient().getName()).isEqualTo("Test Client");
    }

    @Test
    void shouldFindOrdersByDateRange() {
        // Given
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        
        Client client = new Client();
        client.setName("Test Client");
        client = clientRepository.save(client);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(100.0);
        order.setClient(client);
        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        // When
        List<Order> found = orderRepository.findByOrderDateBetween(start, end);

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getId()).isNotNull();
    }

    @Test
    void shouldFindOrdersByTotalAmount() {
        // Given
        Client client = new Client();
        client.setName("Test Client");
        client = clientRepository.save(client);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(150.0);
        order.setClient(client);
        Order savedOrder = orderRepository.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        // When
        List<Order> found = orderRepository.findByTotalAmountGreaterThan(100.0);

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getId()).isNotNull();
        assertThat(found.get(0).getTotalAmount()).isGreaterThan(100.0);
    }
} 