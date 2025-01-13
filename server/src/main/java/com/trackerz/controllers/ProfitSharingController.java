package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.ProfitSharingService;
import com.trackerz.model.ProfitSharing;
import java.util.List;

@RestController
@RequestMapping("/api/profitSharing")
public class ProfitSharingController {

    // Service injection
    private final ProfitSharingService profitSharingService;

    // Constructor injection
    @Autowired
    public ProfitSharingController(ProfitSharingService profitSharingService) {
        this.profitSharingService = profitSharingService;
    }

    // Get all profit sharings
    @GetMapping
    public List<ProfitSharing> getAllProfitSharings() {
        return profitSharingService.getAllProfitSharings();
    }

    // Get a profit sharing by id
    @GetMapping("/{id}")
    public ProfitSharing getProfitSharing(@PathVariable Long id) {
        return profitSharingService.getProfitSharing(id);
    }

    // Create a profit sharing
    @PostMapping("/create")
    public ProfitSharing createProfitSharing(@RequestBody ProfitSharing profitSharing) {
        return profitSharingService.createProfitSharing(profitSharing);
    }

    // Update a profit sharing
    @PutMapping("/update/{id}")
    public ProfitSharing updateProfitSharing(@PathVariable Long id, @RequestBody ProfitSharing profitSharing) {
        return profitSharingService.updateProfitSharing(id, profitSharing);
    }

    // Delete a profit sharing
    @DeleteMapping("/delete/{id}")
    public void deleteProfitSharing(@PathVariable Long id) {
        profitSharingService.deleteProfitSharing(id);
    }
}