package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.OrderService;
import com.trackerz.model.Order;
import com.trackerz.model.OrderProduct;
import java.util.List;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    // Service injection
    private final OrderService orderService;

    // Constructor injection
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Get an order by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    // Get orders between two dates
    @GetMapping("/between/{startDate}/{endDate}")
    public ResponseEntity<List<Order>> getOrdersByDateBetween(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<Order> orders = orderService.getOrdersByDateBetween(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Get orders by total amount
    @GetMapping("/totalAmount/{amount}")
    public ResponseEntity<List<Order>> getOrdersByTotalAmountGreaterThan(@PathVariable Double amount) {
        List<Order> orders = orderService.getOrdersByTotalAmountGreaterThan(amount);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    // Create an order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Update an order
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Add an orderProduct to the list
    @PutMapping("update/orderproduct/add/{id}")
    public ResponseEntity<Order> addOrderProduct(@PathVariable Long id, @Valid @RequestBody OrderProduct orderProduct) {
        Order updatedOrder = orderService.addOrderProduct(id, orderProduct);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    // Remove an orderProduct from the list
    @PutMapping("update/orderproduct/delete/{id}/{orderProductId}")
    public ResponseEntity<Order> deleteOrderProduct(@PathVariable Long id, @PathVariable Long orderProductId) {
        Order updatedOrder = orderService.deleteOrderProduct(id, orderProductId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }
    
}

    

    