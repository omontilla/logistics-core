package com.company.logistics.api;

import com.company.logistics.api.dto.CreateShipmentRequestDTO;
import com.company.logistics.api.dto.ShipmentResponseDTO;
import com.company.logistics.api.dto.UpdateShipmentStatusRequestDTO;
import com.company.logistics.application.CreateShipmentUseCase;
import com.company.logistics.application.GetShipmentByIdUseCase;
import com.company.logistics.application.ListShipmentsUseCase;
import com.company.logistics.application.UpdateShipmentStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipments", description = "Operaciones para gestión de envíos")
public class ShipmentController {

    private final CreateShipmentUseCase createShipmentUseCase;
    private final GetShipmentByIdUseCase getShipmentByIdUseCase;
    private final ListShipmentsUseCase listShipmentsUseCase;
    private final UpdateShipmentStatusUseCase updateShipmentStatusUseCase;

    @Operation(summary = "Crear un nuevo envío")
    @ApiResponse(responseCode = "200", description = "Envío creado correctamente")
    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> createShipment(
            @Valid @RequestBody CreateShipmentRequestDTO request
    ) {
        ShipmentResponseDTO response = createShipmentUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un envío por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(
            @Parameter(description = "ID del envío") @PathVariable Long id
    ) {
        ShipmentResponseDTO response = getShipmentByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar envíos con filtros opcionales")
    @GetMapping
    public ResponseEntity<List<ShipmentResponseDTO>> listShipments(
            @Parameter(description = "Código de estado del envío") @RequestParam(required = false) String status,
            @Parameter(description = "Destino del envío") @RequestParam(required = false) String destination,
            @Parameter(description = "Fecha de creación (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime creationDate
    ) {
        List<ShipmentResponseDTO> result = listShipmentsUseCase.execute(status, destination, creationDate);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Actualizar el estado de un envío")
    @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente")
    @PutMapping("/{id}/status")
    public ResponseEntity<ShipmentResponseDTO> updateStatus(
            @Parameter(description = "ID del envío") @PathVariable Long id,
            @Valid @RequestBody UpdateShipmentStatusRequestDTO request
    ) {
        ShipmentResponseDTO response = updateShipmentStatusUseCase.execute(id, request.getStatusCode());
        return ResponseEntity.ok(response);
    }
}