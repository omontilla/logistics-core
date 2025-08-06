package com.company.logistics.tracking.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShipmentTrackingDTO {

    private Long shipmentId;
    private String status;
    private String location;
    private LocalDateTime timestamp;
}
