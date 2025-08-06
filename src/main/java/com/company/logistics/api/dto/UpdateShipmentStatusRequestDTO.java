package com.company.logistics.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShipmentStatusRequestDTO {
    @NotBlank
    private String statusCode;
}