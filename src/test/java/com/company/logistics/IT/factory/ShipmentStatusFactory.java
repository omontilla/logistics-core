package com.company.logistics.IT.factory;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;

import java.util.Arrays;
import java.util.List;

public class ShipmentStatusFactory {

    public static void createShipmentStatusList(StackIT stackIT) {
        List<ShipmentStatusEntity> defaultStatuses = Arrays.asList(
                new ShipmentStatusEntity(null, "PENDING", "Pendiente"),
                new ShipmentStatusEntity(null, "IN_TRANSIT", "En Tránsito"),
                new ShipmentStatusEntity(null, "DELIVERED", "Entregado")
        );

        stackIT.statusRepository.saveAll(defaultStatuses);
    }

    public static ShipmentStatusEntity createShipmentStatus(StackIT stackIT, String status) {
       return  stackIT.statusRepository.save(new ShipmentStatusEntity(null, status, status));
    }


}
