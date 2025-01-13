package com.trackerz.repository;

import com.trackerz.model.OrderProduct;
import com.trackerz.model.Order;
import com.trackerz.model.Product;
import com.trackerz.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    // Find by order
    List<OrderProduct> findByOrder(Order order);
    
    // Find OrderProduct by product
    List<OrderProduct> findByProduct(Product product);
    
    // Find OrderProduct by status
    List<OrderProduct> findByStatus(OrderStatus status);
    
    // Find <status> products of an order
    List<OrderProduct> findByOrderAndStatus(Order order, OrderStatus status);
    
    // Find orderproducts by product name
    List<OrderProduct> findByProduct_NameContainingIgnoreCase(String productName);
} 