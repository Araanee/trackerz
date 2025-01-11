package com.trackerz.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "groupings")
@Data
public class Grouping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double total;

    @OneToOne
    @JoinColumn(name = "profit_sharing_id", nullable = false)
    private ProfitSharing profitSharing;
    
    @Column(nullable = false)
    private Double nb_orders;

    @OneToMany
    private List<Order> orders;

    private GroupingStatus status;
}