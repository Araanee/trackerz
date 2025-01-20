package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.ProfitSharingService;
import com.trackerz.model.ProfitSharing;
import java.util.List;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ProfitSharing>> getAllProfitSharings() {
        List<ProfitSharing> profitSharings = profitSharingService.getAllProfitSharings();
        return ResponseEntity.status(HttpStatus.OK).body(profitSharings);
    }

    // Get a profit sharing by id
    @GetMapping("/{id}")
    public ResponseEntity<ProfitSharing> getProfitSharing(@PathVariable Long id) {
        ProfitSharing profitSharing = profitSharingService.getProfitSharing(id);
        return ResponseEntity.status(HttpStatus.OK).body(profitSharing);
    }

    // Create a profit sharing
    @PostMapping("/create")
    public ResponseEntity<ProfitSharing> createProfitSharing(@Valid @RequestBody ProfitSharing profitSharing) {
        ProfitSharing createdProfitSharing = profitSharingService.createProfitSharing(profitSharing);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfitSharing);
    }

    // Update a profit sharing
    @PutMapping("/update/{id}")
    public ResponseEntity<ProfitSharing> updateProfitSharing(@PathVariable Long id, @Valid @RequestBody ProfitSharing profitSharing) {
        ProfitSharing updatedProfitSharing = profitSharingService.updateProfitSharing(id, profitSharing);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProfitSharing);
    }

    // Delete a profit sharing
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProfitSharing(@PathVariable Long id) {
        profitSharingService.deleteProfitSharing(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
