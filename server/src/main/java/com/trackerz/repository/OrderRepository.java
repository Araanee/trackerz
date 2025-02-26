package com.trackerz.repository;

import com.trackerz.model.Order;
import com.trackerz.model.Client;
import com.trackerz.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by client
    List<Order> findByClient(Client client);

    // Find orders between two dates
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    // Find orders by total amount
    List<Order> findByTotalAmountGreaterThan(Double amount);

    // Find the order containg a certain orderProduct
    Order findByOrderProductsContaining(OrderProduct orderProduct);
}
