package com.company.logistics.IT.factory;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;

import java.time.LocalDateTime;
import java.util.Random;

public class ShipmentFactory {

    public static ShipmentEntity createShipment(StackIT stackIT, ShipmentStatusEntity status) {
        Random random = new Random();
        
        double randomDistance = 100 + random.nextDouble() * 900;
        double randomEstimatedTime = 1 + random.nextDouble() * 9;

        ShipmentEntity shipment1 = ShipmentEntity.builder()
                .origin("Madrid")
                .destination("Sevilla")
                .creationDate(LocalDateTime.now())
                .distance(Math.round(randomDistance * 100.0) / 100.0)
                .estimatedTime(Math.round(randomEstimatedTime * 100.0) / 100.0)
                .status(status)
                .build();

        return stackIT.shipmentRepository.save(shipment1);
    }
}
