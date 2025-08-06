package com.company.logistics.application;


import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.mapper.ShipmentMapper;
import com.company.logistics.distance.client.DistanceApiClient;
import com.company.logistics.distance.client.dto.DistanceResponse;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.infrastructure.db.repository.ShipmentStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateShipmentUseCase {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusRepository statusRepository;
    private final DistanceApiClient distanceApiClient;

    public ShipmentResponseDTO execute(CreateShipmentRequestDTO request) {
        ShipmentStatusEntity status = statusRepository.findByCode("PENDING")
                .orElseThrow(() -> new IllegalStateException("Default status 'PENDING' not found"));

        DistanceResponse distanceResponse = distanceApiClient.getDistance(request.getOrigin(), request.getDestination());

        ShipmentEntity shipment = ShipmentEntity.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .distance(distanceResponse.getDistanceKm())
                .estimatedTime(distanceResponse.getEstimatedTimeHours())
                .creationDate(LocalDateTime.now())
                .status(status)
                .build();

        ShipmentEntity saved = shipmentRepository.save(shipment);

        return ShipmentMapper.toDto(saved);
    }

}
