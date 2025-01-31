package com.trackerz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_products")
@Data
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "La quantité est obligatoire")
    @Positive(message = "La quantité doit être positive")
    private Integer quantity;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;    
}
