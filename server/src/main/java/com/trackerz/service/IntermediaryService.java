package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.IntermediaryRepository;
import com.trackerz.model.Intermediary;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class IntermediaryService {
    
    // Repository injection
    private final IntermediaryRepository intermediaryRepository;

    // Constructor injection
    @Autowired
    public IntermediaryService(IntermediaryRepository intermediaryRepository) {
        this.intermediaryRepository = intermediaryRepository;
    }

    // Create an intermediary
    public Intermediary createIntermediary(Intermediary intermediary) {
        return intermediaryRepository.save(intermediary);
    }

    // Get intermediary by id
    public Intermediary getIntermediary(Long id) {
        return intermediaryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order product not found with id: " + id));
    }

    // Get intermediary by name
    public List<Intermediary> getIntermediaryByName(String name) {
        return intermediaryRepository.findByNameContainingIgnoreCase(name);
    }

    // Get intermediary by gradeLevel
    public List<Intermediary> getIntermediaryByGradeLevel(Integer gLevel) {
        return intermediaryRepository.findByGradeLevel(gLevel);
    }

    // Get all intermediaries
    public List<Intermediary> getAllIntermediaries() {
        return intermediaryRepository.findAll();
    }

    // Update an intermediary
    public Intermediary updateIntermediary(Long id, Intermediary updatedIntermediary) {
        Intermediary existingIntermediary = getIntermediary(id);
        existingIntermediary.setName(updatedIntermediary.getName());
        existingIntermediary.setGradeLevel(updatedIntermediary.getGradeLevel());
        return intermediaryRepository.save(existingIntermediary);
    }

    // Delete an intermediary
    public void deleteIntermediary(Long id) {
        if (!intermediaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Intermediary not found with id: " + id);
        }
        intermediaryRepository.deleteById(id);
    }

}
