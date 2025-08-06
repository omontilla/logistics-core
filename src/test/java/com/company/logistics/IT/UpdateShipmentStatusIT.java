package com.company.logistics.IT;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.IT.factory.ShipmentFactory;
import com.company.logistics.IT.factory.ShipmentStatusFactory;
import com.company.logistics.application.CreateShipmentUseCase;
import com.company.logistics.application.UpdateShipmentStatusUseCase;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateShipmentStatusIT extends StackIT {

    @Autowired
    private CreateShipmentUseCase createShipmentUseCase;

    @Autowired
    private UpdateShipmentStatusUseCase updateShipmentStatusUseCase;

    @Test
    void shouldUpdateShipmentStatusSuccessfully() {

        ShipmentStatusEntity status = ShipmentStatusFactory.createShipmentStatus(this, "PENDING");
         ShipmentStatusFactory.createShipmentStatus(this, "DELIVERED");
        ShipmentEntity entity = ShipmentFactory.createShipment(this, status);

        // Act
        var updatedShipment = updateShipmentStatusUseCase.execute(entity.getId(), "DELIVERED");

        // Assert
        assertEquals("DELIVERED", updatedShipment.getStatusCode());
        assertEquals(entity.getOrigin(), updatedShipment.getOrigin());
        assertEquals(entity.getDestination(), updatedShipment.getDestination());
    }
}