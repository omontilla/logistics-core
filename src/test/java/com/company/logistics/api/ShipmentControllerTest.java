package com.company.logistics.api;

import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.dto.UpdateShipmentStatusRequestDTO;
import com.company.logistics.application.CreateShipmentUseCase;
import com.company.logistics.application.GetShipmentByIdUseCase;
import com.company.logistics.application.ListShipmentsUseCase;
import com.company.logistics.application.UpdateShipmentStatusUseCase;
import com.company.logistics.util.MockShipmentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ShipmentControllerTest {

    @InjectMocks
    private ShipmentController shipmentController;

    @Mock
    private CreateShipmentUseCase createShipmentUseCase;

    @Mock
    private GetShipmentByIdUseCase getShipmentByIdUseCase;

    @Mock
    private ListShipmentsUseCase listShipmentsUseCase;

    @Mock
    private UpdateShipmentStatusUseCase updateShipmentStatusUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShipment() {
        CreateShipmentRequestDTO request = MockShipmentData.createShipmentRequest();
        ShipmentResponseDTO response = MockShipmentData.shipmentResponse();

        when(createShipmentUseCase.execute(request)).thenReturn(response);

        ResponseEntity<ShipmentResponseDTO> result = shipmentController.createShipment(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void testGetShipmentById() {
        ShipmentResponseDTO response = MockShipmentData.shipmentResponse();

        when(getShipmentByIdUseCase.execute(1L)).thenReturn(response);

        ResponseEntity<ShipmentResponseDTO> result = shipmentController.getShipmentById(1L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void testListShipments() {
        ShipmentResponseDTO response = MockShipmentData.shipmentResponse();

        when(listShipmentsUseCase.execute("PENDING", "Destination", LocalDate.of(2025, 8, 6).atStartOfDay()))
                .thenReturn(List.of(response));

        ResponseEntity<List<ShipmentResponseDTO>> result = shipmentController.listShipments("PENDING", "Destination", LocalDate.of(2025, 8, 6).atStartOfDay());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(response);
    }

    @Test
    void testUpdateStatus() {

        UpdateShipmentStatusRequestDTO request = MockShipmentData.updateStatusToDelivered();
        ShipmentResponseDTO response = MockShipmentData.shipmentResponseUpdate();

        when(updateShipmentStatusUseCase.execute(1L, "DELIVERED")).thenReturn(response);

        ResponseEntity<ShipmentResponseDTO> result = shipmentController.updateStatus(1L, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getStatusCode()).isEqualTo("DELIVERED");
    }
}
