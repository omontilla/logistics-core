package com.company.logistics.tracking.service;

import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.tracking.dto.ShipmentTrackingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShipmentTrackingService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentTrackingDTO getTrackingInfo(Long shipmentId) {
        ShipmentEntity shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        return ShipmentTrackingDTO.builder()
                .shipmentId(shipment.getId())
                .status(shipment.getStatus().getDescription())
                .location(getLocationBasedOnStatus(shipment.getStatus().getCode()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String getLocationBasedOnStatus(String statusCode) {
        return switch (statusCode) {
            case "PENDING" -> "Almacén";
            case "IN_TRANSIT" -> "En tránsito";
            case "DELIVERED" -> "Entregado al destinatario";
            case "CANCELLED" -> "Cancelado";
            default -> "Ubicación desconocida";
        };
    }
}