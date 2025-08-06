package com.company.logistics.application;

import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.distance.client.DistanceApiClient;
import com.company.logistics.distance.client.dto.DistanceResponse;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.infrastructure.db.repository.ShipmentStatusRepository;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateShipmentUseCaseTest {

    private ShipmentRepository shipmentRepository;
    private ShipmentStatusRepository statusRepository;
    private DistanceApiClient distanceApiClient;

    private CreateShipmentUseCase createShipmentUseCase;

    @BeforeEach
    void setUp() {
        shipmentRepository = mock(ShipmentRepository.class);
        statusRepository = mock(ShipmentStatusRepository.class);
        distanceApiClient = mock(DistanceApiClient.class);

        createShipmentUseCase = new CreateShipmentUseCase(
                shipmentRepository,
                statusRepository,
                distanceApiClient
        );
    }

    @Test
    void testCreateShipment_Success() {
        // Arrange
        CreateShipmentRequestDTO request = MockShipmentData.createShipmentRequest();
        ShipmentStatusEntity statusEntity = new ShipmentStatusEntity(1L, "PENDING", "Pendiente");
        DistanceResponse mockDistance = new DistanceResponse(150.5, 2.3);

        when(statusRepository.findByCode("PENDING")).thenReturn(Optional.of(statusEntity));
        when(distanceApiClient.getDistance(request.getOrigin(), request.getDestination()))
                .thenReturn(mockDistance);

        ArgumentCaptor<ShipmentEntity> captor = ArgumentCaptor.forClass(ShipmentEntity.class);
        when(shipmentRepository.save(captor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return saved entity

        // Act
        ShipmentResponseDTO response = createShipmentUseCase.execute(request);

        // Assert
        ShipmentEntity savedEntity = captor.getValue();

        assertNotNull(response);
        assertEquals(request.getOrigin(), savedEntity.getOrigin());
        assertEquals(request.getDestination(), savedEntity.getDestination());
        assertEquals(150.5, savedEntity.getDistance());
        assertEquals(2.3, savedEntity.getEstimatedTime());
        assertEquals("PENDING", savedEntity.getStatus().getCode());
        assertNotNull(savedEntity.getCreationDate());

        verify(shipmentRepository, times(1)).save(any());
    }

    @Test
    void testCreateShipment_StatusNotFound_ThrowsException() {
        // Arrange
        CreateShipmentRequestDTO request = MockShipmentData.createShipmentRequest();
        when(statusRepository.findByCode("PENDING")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            createShipmentUseCase.execute(request);
        });

        assertEquals("Default status 'PENDING' not found", ex.getMessage());
    }
}
