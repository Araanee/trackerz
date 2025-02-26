package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.GroupingRepository;
import com.trackerz.model.Order;
import com.trackerz.model.Grouping;
import com.trackerz.exception.EntityNotFoundException;
import com.trackerz.exception.ObjectAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class GroupingService {
    
    // Repository injection 
    private final GroupingRepository groupingRepository;

    // Constructor injection
    @Autowired
    public GroupingService(GroupingRepository groupingRepository) {
        this.groupingRepository = groupingRepository;
    }

    // Create a grouping
    public Grouping createGrouping(Grouping grouping) {
        return groupingRepository.save(grouping);
    }

    // Get a grouping by id
    public Grouping getGrouping(Long id) {
        return groupingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Grouping not found with id: " + id));
    }

    // Get groupings by name
    public List<Grouping> getGroupingsByName(String name) {
        return groupingRepository.findByNameContainingIgnoreCase(name);
    }

    // Get all groupings
    public List<Grouping> getAllGroupings() {
        return groupingRepository.findAll();
    }

    // Update a grouping
    public Grouping updateGrouping(Long id, Grouping updatedGrouping) {
        Grouping existingGrouping = getGrouping(id);
        existingGrouping.setName(updatedGrouping.getName());
        existingGrouping.setDate(updatedGrouping.getDate());
        existingGrouping.setTotal(updatedGrouping.getTotal());
        existingGrouping.setTotalCollected(updatedGrouping.getTotalCollected());
        existingGrouping.setProfitSharing(updatedGrouping.getProfitSharing());
        existingGrouping.setNb_orders_collected(updatedGrouping.getNb_orders_collected());
        existingGrouping.setNb_orders(updatedGrouping.getNb_orders());
        existingGrouping.setStatus(updatedGrouping.getStatus());
        return groupingRepository.save(existingGrouping);
    }

    // Delete a grouping
    public void deleteGrouping(Long id) {
        if (!groupingRepository.existsById(id)) {
            throw new EntityNotFoundException("Grouping not found with id: " + id);
        }
        groupingRepository.deleteById(id);
    }

    // Add an order to the list
    public Grouping addOrder(Long id, Order order) {
        Grouping updatedGrouping = getGrouping(id);
        if (updatedGrouping.getOrders() == null) {
            updatedGrouping.setOrders(new ArrayList<>());
        }

        // check that an order with this client does not already exists
        boolean clientExists = updatedGrouping.getOrders()
        .stream()
        .anyMatch(o -> o.getClient().getId().equals(order.getClient().getId()));
        if (clientExists) {
            throw new ObjectAlreadyExistsException("Order with client : " +  order.getClient().getName() + " already exists.");
        }

        updatedGrouping.getOrders().add(order);
        updatedGrouping.setNb_orders(updatedGrouping.getNb_orders() + 1);
        return groupingRepository.save(updatedGrouping);
    }

    // Remove an order from the list
    public Grouping deleteOrder(Long id, Long orderId) {
        Grouping updatedGrouping = getGrouping(id);
        updatedGrouping.getOrders().removeIf(o -> o.getId().equals(orderId));
        return groupingRepository.save(updatedGrouping);
    }
    
}