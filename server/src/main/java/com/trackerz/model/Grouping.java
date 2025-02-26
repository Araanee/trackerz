package com.trackerz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 100, message = "Le nom du groupement doit contenir entre 3 et 100 caractères")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private Double totalCollected;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profit_sharing_id", nullable = false)
    private ProfitSharing profitSharing;
    
    @Column(nullable = false)
    private Integer nb_orders_collected;

    @Column(nullable = false)
    private Integer nb_orders;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> orders;

    @NotNull(message = "Le statut est obligatoire")
    private GroupingStatus status;
}