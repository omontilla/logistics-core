package com.company.logistics.IT;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.IT.factory.ShipmentFactory;
import com.company.logistics.IT.factory.ShipmentStatusFactory;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.application.ListShipmentsUseCase;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListShipmentsIT extends StackIT {

    @Autowired
    private ListShipmentsUseCase listShipmentsUseCase;

    @Test
    void shouldReturnFilteredShipments() {

        ShipmentStatusEntity status = ShipmentStatusFactory.createShipmentStatus(this, "PENDING");

        ShipmentFactory.createShipment(this,status);
        ShipmentFactory.createShipment(this,status);

        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Act
        List<ShipmentResponseDTO> results = listShipmentsUseCase.execute("PENDING", "Sevilla", startOfDay);

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(s -> s.getStatusCode().equals("PENDING")));
        assertTrue(results.stream().allMatch(s -> s.getDestination().equals("Sevilla")));
    }
}
