package com.company.logistics.IT;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.IT.factory.ShipmentFactory;
import com.company.logistics.IT.factory.ShipmentStatusFactory;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.application.GetShipmentByIdUseCase;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class GetShipmentByIdIT extends StackIT {

    @Autowired
    private GetShipmentByIdUseCase getShipmentByIdUseCase;


    @Test
    void shouldReturnShipmentById() {
        ShipmentStatusEntity status = ShipmentStatusFactory.createShipmentStatus(this,"PENDING");
        ShipmentEntity entity = ShipmentFactory.createShipment(this, status);

        // Act
        ShipmentResponseDTO response = getShipmentByIdUseCase.execute(entity.getId());

        // Assert
        assertEquals(entity.getId(), response.getId());
        assertEquals("Madrid", response.getOrigin());
        assertEquals("Sevilla", response.getDestination());
        assertEquals("PENDING", response.getStatusCode());
    }
}
