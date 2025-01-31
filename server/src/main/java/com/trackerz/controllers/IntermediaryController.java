package com.trackerz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.trackerz.service.IntermediaryService;
import com.trackerz.model.Intermediary;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/intermediaries")
public class IntermediaryController {
    
    // Service injection
    private final IntermediaryService intermediaryService;

    // Constructor injection
    @Autowired
    public IntermediaryController(IntermediaryService intermediaryService) {
        this.intermediaryService = intermediaryService;
    }

    // Get intermediary by id
    @GetMapping("/{id}")
    public ResponseEntity<Intermediary> getIntermediary(@PathVariable Long id) {
        Intermediary intermediary = intermediaryService.getIntermediary(id);
        return ResponseEntity.status(HttpStatus.OK).body(intermediary);
    }

    // Get intermediary by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Intermediary>> getIntermediaryByName(@PathVariable String name) {
        List<Intermediary> intermediaries = intermediaryService.getIntermediaryByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(intermediaries);
    }

    // Get intermediary by gradeLevel
    @GetMapping("/gradeLevel/{gLevel}")
    public ResponseEntity<List<Intermediary>> getIntermediaryByGradeLevel(@PathVariable Integer gLevel) {
        List<Intermediary> intermediaries = intermediaryService.getIntermediaryByGradeLevel(gLevel);
        return ResponseEntity.status(HttpStatus.OK).body(intermediaries);
    }

    // Get all intermediaries
    @GetMapping
    public ResponseEntity<List<Intermediary>> getAllIntermediaries() {
        List<Intermediary> intermediaries = intermediaryService.getAllIntermediaries();
        return ResponseEntity.status(HttpStatus.OK).body(intermediaries);
    }

    // Create an intermediary
    @PostMapping("/create")
    public ResponseEntity<Intermediary> createIntermediary(@Valid @RequestBody Intermediary intermediary) {
        Intermediary createdIntermediary = intermediaryService.createIntermediary(intermediary);
        return ResponseEntity.status(HttpStatus.OK).body(createdIntermediary);
    }

    // Update an intermediary
    @PutMapping("/update/{id}")
    public ResponseEntity<Intermediary> updateIntermediary(@PathVariable Long id, @Valid @RequestBody Intermediary intermediary) {
        Intermediary updatedIntermediary = intermediaryService.updateIntermediary(id, intermediary);
        return ResponseEntity.status(HttpStatus.OK).body(updatedIntermediary);
    }

    // Delete an intermediary
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIntermediary(@PathVariable Long id) {
        intermediaryService.deleteIntermediary(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
