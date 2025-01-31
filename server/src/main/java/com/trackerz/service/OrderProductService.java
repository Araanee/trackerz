package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.OrderProductRepository;
import com.trackerz.model.OrderProduct;
import com.trackerz.model.OrderStatus;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class OrderProductService {
    
    // Repository injection 
    private final OrderProductRepository orderProductRepository;

    // Constructor injection
    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    // Create an order product
    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    // Get an order product by id
    public OrderProduct getOrderProduct(Long id) {
        return orderProductRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order product not found with id: " + id));
    }

    // Get products by status
    public List<OrderProduct> getOrderProductsByStatus(OrderStatus status) {
        return orderProductRepository.findByStatus(status);
    }

    // Get order products by product name
    public List<OrderProduct> getOrderProductsByProductName(String productName) {
        return orderProductRepository.findByProduct_NameContainingIgnoreCase(productName);
    }

    // Find all order product associated to an intermediary
    public List<OrderProduct> getOrderProductsByIntermediary(Long intermediaryId) {
        return orderProductRepository.findByIntermediaryId(intermediaryId);
    }

    // Get all order products
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    // Update an order product
    public OrderProduct updateOrderProduct(Long id, OrderProduct updatedOrderProduct) {
        OrderProduct existingOrderProduct = getOrderProduct(id);
        existingOrderProduct.setProduct(updatedOrderProduct.getProduct());
        existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
        existingOrderProduct.setStatus(updatedOrderProduct.getStatus());
        existingOrderProduct.setDeliveryDate(updatedOrderProduct.getDeliveryDate());
        return orderProductRepository.save(existingOrderProduct);
    }

    // Delete an order product
    public void deleteOrderProduct(Long id) {
        if (!orderProductRepository.existsById(id)) {
            throw new EntityNotFoundException("Order product not found with id: " + id);
        }
        orderProductRepository.deleteById(id);
    }
}