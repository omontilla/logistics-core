package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.mapper.ShipmentMapper;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GetShipmentByIdUseCase {

    private final ShipmentRepository shipmentRepository;

    public ShipmentResponseDTO execute(Long id) {
        ShipmentEntity entity = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found"));

        return ShipmentMapper.toDto(entity);
    }
}