package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.OrderService;
import com.trackerz.model.Order;
import java.util.List;
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
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get an order by id
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    // Get orders between two dates
    @GetMapping("/between/{startDate}/{endDate}")
    public List<Order> getOrdersByDateBetween(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        return orderService.getOrdersByDateBetween(startDate, endDate);
    }

    // Get orders by total amount
    @GetMapping("/totalAmount/{amount}")
    public List<Order> getOrdersByTotalAmountGreaterThan(@PathVariable Double amount) {
        return orderService.getOrdersByTotalAmountGreaterThan(amount);
    }

    // Create an order
    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Update an order
    @PutMapping("/update/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}

    

    