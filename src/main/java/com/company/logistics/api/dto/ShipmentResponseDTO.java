package com.company.logistics.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponseDTO {
    private Long id;
    private String origin;
    private String destination;
    private Double distance;
    private Double estimatedTime;
    private String statusCode;
    private LocalDateTime creationDate;
}