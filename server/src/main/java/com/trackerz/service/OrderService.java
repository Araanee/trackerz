package com.trackerz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.trackerz.repository.OrderProductRepository;
import com.trackerz.repository.OrderRepository;
import com.trackerz.model.Order;
import com.trackerz.model.OrderProduct;
import com.trackerz.model.OrderStatus;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Service
@Transactional
public class OrderService {
    
    // Repository injection
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    // Constructor injection
    @Autowired
    public OrderService(
        OrderRepository orderRepository,
        OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    // Create an order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get client by id
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get orders between two dates
    public List<Order> getOrdersByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    // Get orders by total amount
    public List<Order> getOrdersByTotalAmountGreaterThan(Double amount) {
        return orderRepository.findByTotalAmountGreaterThan(amount);
    }

    // Update an order
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrder(id);
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
        existingOrder.setClient(updatedOrder.getClient());
        return orderRepository.save(existingOrder);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    // add an order product to the list
    public Order addOrderProduct(Long id, OrderProduct orderProduct) {
        Order order = getOrder(id);
        if (order.getOrderProducts() == null) {
            order.setOrderProducts(new ArrayList<OrderProduct>());
        }

        

        order.getOrderProducts().add(orderProduct);
        order.setTotalAmount(order.getTotalAmount() + orderProduct.getProduct().getPrice());
        return orderRepository.save(order);
    }

    // remove an orderProduct from the list
    public Order deleteOrderProduct(Long id, Long orderProductId) {
        Order order = getOrder(id);
        boolean isInTheList = order.getOrderProducts().removeIf(op -> op.getId().equals(orderProductId));
        // Substract the price from the total amount of the order
        if (isInTheList) {
            OrderProduct orderProduct = orderProductRepository.getReferenceById(orderProductId);
            order.setTotalAmount(order.getTotalAmount() - orderProduct.getProduct().getPrice());
            if (orderProduct.getStatus() == OrderStatus.COLLECTED) {
                order.setTotalCollected(order.getTotalCollected() - orderProduct.getProduct().getPrice());
            }
        }
        return orderRepository.save(order);
    }
}