package com.company.logistics.api.mapper;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;

public class ShipmentMapper {

    private ShipmentMapper() {
    }

    public static ShipmentResponseDTO toDto(ShipmentEntity entity) {
        return ShipmentResponseDTO.builder()
                .id(entity.getId())
                .origin(entity.getOrigin())
                .destination(entity.getDestination())
                .distance(entity.getDistance())
                .estimatedTime(entity.getEstimatedTime())
                .statusCode(entity.getStatus().getCode())
                .creationDate(entity.getCreationDate())
                .build();
    }
}
