package com.alarkon.garden.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seed_packet_id")
    private SeedPacket seedPacket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private PlantStatus status;
    private LocalDateTime plantedAt;
    private LocalDateTime matureAt;
    private LocalDateTime harvestedAt;
    private int actualSellPrice;

}
