package com.company.logistics.application;

import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import com.company.logistics.infrastructure.db.repository.ShipmentRepository;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListShipmentsUseCaseTest {

    private ShipmentRepository shipmentRepository;
    private ListShipmentsUseCase listShipmentsUseCase;

    @BeforeEach
    void setUp() {
        shipmentRepository = mock(ShipmentRepository.class);
        listShipmentsUseCase = new ListShipmentsUseCase(shipmentRepository);
    }

    @Test
    void testListShipmentsWithFilters() {
        // Arrange
        ShipmentEntity shipment = MockShipmentData.createEntity();
        when(shipmentRepository.findAllWithFilters("PENDING", "Barcelona", LocalDate.of(2025, 8, 5).atStartOfDay()))
                .thenReturn(List.of(shipment));

        // Act
        List<ShipmentResponseDTO> results = listShipmentsUseCase.execute("PENDING", "Barcelona", LocalDate.of(2025, 8, 5).atStartOfDay());

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        ShipmentResponseDTO dto = results.get(0);
        assertEquals("Madrid", dto.getOrigin());
        assertEquals("Barcelona", dto.getDestination());
    }

    @Test
    void testListShipmentsReturnsEmptyList() {
        // Arrange
        when(shipmentRepository.findAllWithFilters(null, null, null))
                .thenReturn(List.of());

        // Act
        List<ShipmentResponseDTO> results = listShipmentsUseCase.execute(null, null, null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
