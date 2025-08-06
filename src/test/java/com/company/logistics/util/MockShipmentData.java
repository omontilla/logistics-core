package com.company.logistics.util;


import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.dto.UpdateShipmentStatusRequestDTO;
import com.company.logistics.distance.client.dto.DistanceResponse;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MockShipmentData {

    public static CreateShipmentRequestDTO createShipmentRequest() {
        return CreateShipmentRequestDTO.builder()
                .origin("Madrid")
                .destination("Barcelona")
                .build();
    }

    public static ShipmentResponseDTO shipmentResponse() {
        return ShipmentResponseDTO.builder()
                .id(1L)
                .origin("Madrid")
                .destination("Barcelona")
                .distance(620.5)
                .statusCode("PENDING")
                .creationDate(LocalDate.from(LocalDate.of(2025, 8, 6).atStartOfDay()).atStartOfDay())
                .estimatedTime(6.0)
                .build();
    }

    public static ShipmentResponseDTO shipmentResponseUpdate() {
        return ShipmentResponseDTO.builder()
                .id(1L)
                .origin("Madrid")
                .destination("Barcelona")
                .distance(620.5)
                .statusCode("DELIVERED")
                .creationDate(LocalDate.from(LocalDate.of(2025, 8, 6).atStartOfDay()).atStartOfDay())
                .estimatedTime(6.0)
                .build();
    }

    public static UpdateShipmentStatusRequestDTO updateStatusToDelivered() {
        return UpdateShipmentStatusRequestDTO.builder()
                .statusCode("DELIVERED")
                .build();
    }

    public static UpdateShipmentStatusRequestDTO updateStatusToInTransit() {
        return UpdateShipmentStatusRequestDTO.builder()
                .statusCode("IN_TRANSIT")
                .build();
    }
    public static ShipmentEntity createEntity() {
        ShipmentStatusEntity status = new ShipmentStatusEntity(1L, "PENDING", "Pendiente");
        return ShipmentEntity.builder()
                .id(1L)
                .origin("Madrid")
                .destination("Barcelona")
                .status(status)
                .distance(620.0)
                .estimatedTime(6.0)
                .creationDate(LocalDateTime.now())
                .build();
    }


    public static DistanceResponse mockDistanceResponse = DistanceResponse.builder()
            .distanceKm(620.5)
            .estimatedTimeHours(0.8)
            .build();

}
