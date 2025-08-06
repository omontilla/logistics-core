package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.mapper.ShipmentMapper;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListShipmentsUseCase {

    private final ShipmentRepository shipmentRepository;

    public List<ShipmentResponseDTO> execute(String status, String destination, LocalDateTime creationDate) {

        if (creationDate == null) {
            creationDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        }


        List<ShipmentEntity> results = shipmentRepository.findAllWithFilters(status, destination, creationDate);

        return results.stream()
                .map(ShipmentMapper::toDto)
                .collect(Collectors.toList());
    }
}