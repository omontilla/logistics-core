package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.infrastructure.db.repository.ShipmentStatusRepository;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateShipmentStatusUseCaseTest {

    private ShipmentRepository shipmentRepository;
    private ShipmentStatusRepository statusRepository;
    private UpdateShipmentStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        shipmentRepository = mock(ShipmentRepository.class);
        statusRepository = mock(ShipmentStatusRepository.class);
        useCase = new UpdateShipmentStatusUseCase(shipmentRepository, statusRepository);
    }

    @Test
    void testUpdateShipmentStatusSuccess() {
        // Arrange
        ShipmentEntity shipment = MockShipmentData.createEntity();
        ShipmentStatusEntity delivered = new ShipmentStatusEntity(2L, "DELIVERED", "Entregado");

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(statusRepository.findByCode("DELIVERED")).thenReturn(Optional.of(delivered));
        when(shipmentRepository.save(any())).thenReturn(shipment); // después de modificar status

        // Act
        ShipmentResponseDTO result = useCase.execute(1L, "DELIVERED");

        // Assert
        assertNotNull(result);
        assertEquals("Barcelona", result.getDestination());
        assertEquals("DELIVERED", result.getStatusCode()); // por descripción
        verify(shipmentRepository).save(shipment);
    }

    @Test
    void testShipmentNotFound() {
        when(shipmentRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            useCase.execute(99L, "DELIVERED");
        });

        assertEquals("404 NOT_FOUND \"Shipment not found\"", ex.getMessage());
    }

    @Test
    void testInvalidStatus() {
        ShipmentEntity shipment = MockShipmentData.createEntity();

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(statusRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            useCase.execute(1L, "INVALID");
        });

        assertEquals("400 BAD_REQUEST \"Invalid status\"", ex.getMessage());
    }
}
