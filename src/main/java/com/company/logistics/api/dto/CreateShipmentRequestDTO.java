package com.company.logistics.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateShipmentRequestDTO {

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    private Double distance;
}