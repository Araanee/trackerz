package com.trackerz.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import com.trackerz.repository.ProfitSharingRepository;
import com.trackerz.model.ProfitSharing;
import com.trackerz.exception.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ProfitSharingService {
    
    // Repository injection
    private final ProfitSharingRepository profitSharingRepository;

    // Constructor injection
    @Autowired
    public ProfitSharingService(ProfitSharingRepository profitSharingRepository) {
        this.profitSharingRepository = profitSharingRepository;
    }

    // Create a profit sharing
    public ProfitSharing createProfitSharing(ProfitSharing profitSharing) {
        return profitSharingRepository.save(profitSharing);
    }

    // Get a profit sharing by id
    public ProfitSharing getProfitSharing(Long id) {
        return profitSharingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Profit sharing not found with id: " + id));
    }

    // Get all profit sharings
    public List<ProfitSharing> getAllProfitSharings() {
        return profitSharingRepository.findAll();
    }

    // Update a profit sharing
    public ProfitSharing updateProfitSharing(Long id, ProfitSharing updatedProfitSharing) {
        ProfitSharing existingProfitSharing = getProfitSharing(id);
        // check if the profit sharing exists
        if (existingProfitSharing == null) {
            throw new EntityNotFoundException("Profit sharing not found with id: " + id);
        }
        existingProfitSharing.setTotal(updatedProfitSharing.getTotal());
        existingProfitSharing.setPerson1Share(updatedProfitSharing.getPerson1Share());
        existingProfitSharing.setPerson2Share(updatedProfitSharing.getPerson2Share());
        existingProfitSharing.setPerson3Share(updatedProfitSharing.getPerson3Share());
        return profitSharingRepository.save(existingProfitSharing);
    }

    // Delete a profit sharing
    public void deleteProfitSharing(Long id) {
        if (!profitSharingRepository.existsById(id)) {
            throw new EntityNotFoundException("Profit sharing not found with id: " + id);
        }
        profitSharingRepository.deleteById(id);
    }
}