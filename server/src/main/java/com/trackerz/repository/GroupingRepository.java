package com.trackerz.repository;

import com.trackerz.model.Grouping;
import com.trackerz.model.GroupingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroupingRepository extends JpaRepository<Grouping, Long> {    
    // Find groupings by status
    List<Grouping> findByStatus(GroupingStatus status);

    // Find groupings between two dates
    List<Grouping> findByDateBetween(LocalDateTime start, LocalDateTime end);
    
    // Add this method
    List<Grouping> findByNameContainingIgnoreCase(String name);
} 