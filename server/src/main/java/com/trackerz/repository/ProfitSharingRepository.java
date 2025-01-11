package com.trackerz.repository;

import com.trackerz.model.ProfitSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProfitSharingRepository extends JpaRepository<ProfitSharing, Long> {
    // Find profit sharing by total amount  
    List<ProfitSharing> findByTotalGreaterThan(Double amount);

    // Find profit sharing by person1 amount
    List<ProfitSharing> findByPerson1ShareGreaterThan(Double amount);

    // Find profit sharing by person2 amount
    List<ProfitSharing> findByPerson2ShareGreaterThan(Double amount);

    // Find profit sharing by person3 amount
    List<ProfitSharing> findByPerson3ShareGreaterThan(Double amount);
} 