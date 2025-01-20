package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.GroupingService;
import com.trackerz.model.Grouping;
import java.util.List;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/groupings")
public class GroupingController {
    
    // Service injection
    private final GroupingService groupingService;

    // Constructor injection
    @Autowired
    public GroupingController(GroupingService groupingService) {
        this.groupingService = groupingService;
    }

    // Get all groupings
    @GetMapping
    public ResponseEntity<List<Grouping>> getAllGroupings() {
        List<Grouping> groupings = groupingService.getAllGroupings();
        return ResponseEntity.status(HttpStatus.OK).body(groupings);
    }

    // Get a grouping by id
    @GetMapping("/{id}")
    public ResponseEntity<Grouping> getGrouping(@PathVariable Long id) {
        Grouping grouping = groupingService.getGrouping(id);
        return ResponseEntity.status(HttpStatus.OK).body(grouping);
    }

    // Get groupings by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Grouping>> getGroupingsByName(@PathVariable String name) {
        List<Grouping> groupings = groupingService.getGroupingsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(groupings);
    }

    // Create a grouping
    @PostMapping("/create")
    public ResponseEntity<Grouping> createGrouping(@Valid @RequestBody Grouping grouping) {
        Grouping createdGrouping = groupingService.createGrouping(grouping);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrouping);
    }

    // Update a grouping
    @PutMapping("/update/{id}")
    public ResponseEntity<Grouping> updateGrouping(@PathVariable Long id, @Valid @RequestBody Grouping grouping) {
        Grouping updatedGrouping = groupingService.updateGrouping(id, grouping);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGrouping);
    }

    // Delete a grouping
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGrouping(@PathVariable Long id) {
        groupingService.deleteGrouping(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
