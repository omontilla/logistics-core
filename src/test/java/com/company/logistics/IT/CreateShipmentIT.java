package com.company.logistics.IT;

import com.company.logistics.IT.dependency.StackIT;
import com.company.logistics.IT.factory.ShipmentStatusFactory;
import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.application.CreateShipmentUseCase;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class CreateShipmentIT extends StackIT {

    @Autowired
    private CreateShipmentUseCase createShipmentUseCase;

    @Test
    void shouldCreateShipmentSuccessfully() {
        // Arrange
        CreateShipmentRequestDTO request = MockShipmentData.createShipmentRequest();
        ShipmentStatusFactory.createShipmentStatusList(this);

        // Act
        ShipmentResponseDTO response = createShipmentUseCase.execute(request);

        // Assert
        assertNotNull(response.getId());
        assertEquals(request.getOrigin(), response.getOrigin());
        assertEquals("PENDING", response.getStatusCode());

    }
}