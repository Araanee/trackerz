package com.trackerz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profit_sharing")
@Data
public class ProfitSharing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double total;

    @Column(name = "person1_share")
    private Double person1Share;

    @Column(name = "person2_share")
    private Double person2Share;

    @Column(name = "person3_share")
    private Double person3Share;
}
