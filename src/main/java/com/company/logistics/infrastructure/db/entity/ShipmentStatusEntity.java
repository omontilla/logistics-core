package com.company.logistics.infrastructure.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipment_status")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // Ej: "PENDING", "IN_TRANSIT", "DELIVERED"

    private String description;

}