package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetShipmentByIdUseCaseTest {

    private ShipmentRepository shipmentRepository;
    private GetShipmentByIdUseCase getShipmentByIdUseCase;

    @BeforeEach
    void setUp() {
        shipmentRepository = mock(ShipmentRepository.class);
        getShipmentByIdUseCase = new GetShipmentByIdUseCase(shipmentRepository);
    }

    @Test
    void testGetShipmentById_Success() {
        // Arrange
        ShipmentEntity entity = MockShipmentData.createEntity();
        Long shipmentId = entity.getId();

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(entity));

        // Act
        ShipmentResponseDTO response = getShipmentByIdUseCase.execute(shipmentId);

        // Assert
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getOrigin(), response.getOrigin());
        assertEquals(entity.getDestination(), response.getDestination());
    }

    @Test
    void testGetShipmentById_NotFound_ThrowsException() {
        // Arrange
        Long shipmentId = 999L;
        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            getShipmentByIdUseCase.execute(shipmentId);
        });

        assertEquals("404 NOT_FOUND \"Shipment not found\"", ex.getMessage());
    }
}
