package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.OrderProductService;
import com.trackerz.model.OrderProduct;
import com.trackerz.model.OrderStatus;
import java.util.List;

@RestController
@RequestMapping("/api/orderProducts")
public class OrderProductController {
    
    // Service injection
    private final OrderProductService orderProductService;

    // Constructor injection
    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    // Get all order products
    @GetMapping
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductService.getAllOrderProducts();
    }

    // Get an order product by id
    @GetMapping("/{id}")
    public OrderProduct getOrderProduct(@PathVariable Long id) {
        return orderProductService.getOrderProduct(id);
    }

    // Get order products by status
    @GetMapping("/status/{status}")
    public List<OrderProduct> getOrderProductsByStatus(@PathVariable OrderStatus status) {
        return orderProductService.getOrderProductsByStatus(status);
    }

    // Get order products by product name
    @GetMapping("/productName/{productName}")
    public List<OrderProduct> getOrderProductsByProductName(@PathVariable String productName) {
        return orderProductService.getOrderProductsByProductName(productName);
    }

    // Create an order product
    @PostMapping("/create")
    public OrderProduct createOrderProduct(@RequestBody OrderProduct orderProduct) {
        return orderProductService.createOrderProduct(orderProduct);
    }

    // Update an order product
    @PutMapping("/update/{id}")
    public OrderProduct updateOrderProduct(@PathVariable Long id, @RequestBody OrderProduct orderProduct) {
        return orderProductService.updateOrderProduct(id, orderProduct);
    }

    // Delete an order product
    @DeleteMapping("/delete/{id}")
    public void deleteOrderProduct(@PathVariable Long id) {
        orderProductService.deleteOrderProduct(id);
    }

}