package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.GroupingRepository;
import com.trackerz.repository.OrderProductRepository;
import com.trackerz.repository.OrderRepository;
import com.trackerz.model.Grouping;
import com.trackerz.model.Order;
import com.trackerz.model.OrderProduct;
import com.trackerz.model.OrderStatus;
import com.trackerz.model.ProfitSharing;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class OrderProductService {
    
    // Repository injection 
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final GroupingRepository groupingRepository;
    private final ProfitSharingService profitSharingService;

    // Constructor injection
    @Autowired
    public OrderProductService(
            OrderProductRepository orderProductRepository,
            OrderRepository orderRepository,
            GroupingRepository groupingRepository,
            ProfitSharingService profitSharingService) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.groupingRepository = groupingRepository;
        this.profitSharingService = profitSharingService;
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
    @Transactional
    public OrderProduct updateOrderProduct(Long id, OrderProduct updatedOrderProduct) {
        OrderProduct existingOrderProduct = getOrderProduct(id);

        boolean isNewStatusCollected = existingOrderProduct.getStatus() != OrderStatus.COLLECTED
        && updatedOrderProduct.getStatus() == OrderStatus.COLLECTED;

        existingOrderProduct.setProduct(updatedOrderProduct.getProduct());
        existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
        existingOrderProduct.setStatus(updatedOrderProduct.getStatus());
        existingOrderProduct.setDeliveryDate(updatedOrderProduct.getDeliveryDate());
        
        OrderProduct orderProduct = orderProductRepository.save(existingOrderProduct);

        if (isNewStatusCollected) {
            updateTotalAndProfit(orderProduct);
        }

        return orderProduct;
    }

    private void updateTotalAndProfit(OrderProduct orderProduct) {
        // Get the parent order
        Order parentOrder = orderRepository.findByOrderProductsContaining(orderProduct);
        if (parentOrder == null) {
            throw new EntityNotFoundException("Parent Order not found for OrderProduct: " + orderProduct.getId());
        }
        
        // Get the amount to add
        Double amount = (orderProduct.getProduct().getPrice() != null ? orderProduct.getProduct().getPrice() : 0.0) * orderProduct.getQuantity();
        
        // Update the totalCollected property of the order
        Double newTotalCollected = (parentOrder.getTotalCollected() != null ? parentOrder.getTotalCollected() : 0.0) + amount;
        parentOrder.setTotalCollected(newTotalCollected);
        orderRepository.save(parentOrder);
        
        // Get the grouping of the order to update the profit sharing
        Grouping grouping = findGroupingForOrder(parentOrder);
        if (grouping != null) {
            ProfitSharing profitSharing = grouping.getProfitSharing();
            
            // Divide the amount between the different intermediaries
            applyProfitSharingRules(profitSharing, orderProduct, amount);
            
            // update the profitSHaring
            profitSharingService.updateProfitSharing(profitSharing.getId(), profitSharing);
            
            // Update the grouping stats
            grouping.setTotalCollected((grouping.getTotalCollected() != null ? grouping.getTotalCollected() : 0.0) + amount);
            grouping.setNb_orders_collected((grouping.getNb_orders_collected() != null ? grouping.getNb_orders_collected() : 0) + 1);
            groupingRepository.save(grouping);
        }
    }
    
    // Get the grouping associated to an order
    private Grouping findGroupingForOrder(Order order) {
        return groupingRepository.findByOrdersContaining(order);
    }
    
    // Apply profit sharing rules
    private void applyProfitSharingRules(ProfitSharing profitSharing, OrderProduct orderProduct, Double amount) {
        // Update the total amount
        profitSharing.setTotal((profitSharing.getTotal() != null ? profitSharing.getTotal() : 0.0) + amount);
        
        // get the gradeLevel of the intermediary
        Integer gradeLevel = orderProduct.getIntermediary().getGradeLevel();
        
        // Initialize the share parts to 0 if null
        if (profitSharing.getPerson1Share() == null) profitSharing.setPerson1Share(0.0);
        if (profitSharing.getPerson2Share() == null) profitSharing.setPerson2Share(0.0);
        if (profitSharing.getPerson3Share() == null) profitSharing.setPerson3Share(0.0);
        
        // Apply the repartition rules
        Double price = orderProduct.getProduct().getPrice() != null ? orderProduct.getProduct().getPrice() : 0.0;
        if (gradeLevel < 3 && price > 100) {
            // If gradeLevel is 1 or 2 and the price  > 100 => incr PersonXShare by 20
            if (gradeLevel == 1) {
                profitSharing.setPerson1Share(profitSharing.getPerson1Share() + 20.0);
            } else if (gradeLevel == 2) {
                profitSharing.setPerson2Share(profitSharing.getPerson2Share() + 20.0);
            }
            profitSharing.setPerson3Share(profitSharing.getPerson3Share() + (amount - 20.0));

        } else {
            // else all the profit goes to Person3
            profitSharing.setPerson3Share(profitSharing.getPerson3Share() + amount);
        }
    }

    // Delete an order product
    public void deleteOrderProduct(Long id) {
        if (!orderProductRepository.existsById(id)) {
            throw new EntityNotFoundException("Order product not found with id: " + id);
        }
        orderProductRepository.deleteById(id);
    }
}