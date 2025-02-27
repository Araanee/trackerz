package com.trackerz.repository;

import com.trackerz.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Search by name
    List<Client> findByNameContainingIgnoreCase(String name);
    
    // Search by phone number
    Optional<Client> findByPhoneNumber(String phoneNumber);
}