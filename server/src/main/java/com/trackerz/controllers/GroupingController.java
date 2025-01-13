package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.GroupingService;
import com.trackerz.model.Grouping;
import java.util.List;

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
    public List<Grouping> getAllGroupings() {
        return groupingService.getAllGroupings();
    }

    // Get a grouping by id
    @GetMapping("/{id}")
    public Grouping getGrouping(@PathVariable Long id) {
        return groupingService.getGrouping(id);
    }

    // Get groupings by name
    @GetMapping("/name/{name}")
    public List<Grouping> getGroupingsByName(@PathVariable String name) {
        return groupingService.getGroupingsByName(name);
    }

    // Create a grouping
    @PostMapping("/create")
    public Grouping createGrouping(@RequestBody Grouping grouping) {
        return groupingService.createGrouping(grouping);
    }

    // Update a grouping
    @PutMapping("/update/{id}")
    public Grouping updateGrouping(@PathVariable Long id, @RequestBody Grouping grouping) {
        return groupingService.updateGrouping(id, grouping);
    }

    // Delete a grouping
    @DeleteMapping("/delete/{id}")
    public void deleteGrouping(@PathVariable Long id) {
        groupingService.deleteGrouping(id);
    }
}
