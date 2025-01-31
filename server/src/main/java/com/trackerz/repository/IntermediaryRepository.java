package com.trackerz.repository;

import com.trackerz.model.Intermediary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntermediaryRepository extends JpaRepository<Intermediary, Long> {
    // Find intermediaries by name
    List<Intermediary> findByNameContainingIgnoreCase(String name);

    // Find by gradeLevel
    List<Intermediary> findByGradeLevel(Integer gLevel);
}
