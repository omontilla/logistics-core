package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.mapper.ShipmentMapper;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.infrastructure.db.repository.ShipmentStatusRepository;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UpdateShipmentStatusUseCase {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentStatusRepository statusRepository;

    public ShipmentResponseDTO execute(Long shipmentId, String statusCode) {
        ShipmentEntity shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found"));

        ShipmentStatusEntity newStatus = statusRepository.findByCode(statusCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status"));

        shipment.setStatus(newStatus);
        ShipmentEntity entity = shipmentRepository.save(shipment);

        return ShipmentMapper.toDto(entity);
    }
}