package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.OrderProductService;
import com.trackerz.model.OrderProduct;
import com.trackerz.model.OrderStatus;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
        return ResponseEntity.status(HttpStatus.OK).body(orderProducts);
    }

    // Get an order product by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getOrderProduct(@PathVariable Long id) {
        OrderProduct orderProduct = orderProductService.getOrderProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderProduct);
    }

    // Get order products by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderProduct>> getOrderProductsByStatus(@PathVariable OrderStatus status) {
        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(orderProducts);
    }

    // Get order products by product name
    @GetMapping("/productName/{productName}")
    public ResponseEntity<List<OrderProduct>> getOrderProductsByProductName(@PathVariable String productName) {
        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByProductName(productName);
        return ResponseEntity.status(HttpStatus.OK).body(orderProducts);
    }

    // Get all order product associated with an intermediary
    @GetMapping("/intermediary/{id}")
    public ResponseEntity<List<OrderProduct>> getCommandes(@PathVariable Long id) {
        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByIntermediary(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderProducts);
    }

    // Create an order product
    @PostMapping("/create")
    public ResponseEntity<OrderProduct> createOrderProduct(@Valid @RequestBody OrderProduct orderProduct) {
        OrderProduct createdOrderProduct = orderProductService.createOrderProduct(orderProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderProduct);
    }

    // Update an order product
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderProduct> updateOrderProduct(@PathVariable Long id, @Valid @RequestBody OrderProduct orderProduct) {
        OrderProduct updatedOrderProduct = orderProductService.updateOrderProduct(id, orderProduct);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrderProduct);
    }

    // Delete an order product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable Long id) {
        orderProductService.deleteOrderProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}