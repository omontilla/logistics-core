package com.company.logistics.tracking;

import com.company.logistics.tracking.dto.ShipmentTrackingDTO;
import com.company.logistics.tracking.service.ShipmentTrackingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.*;


@RequiredArgsConstructor
public class ShipmentTrackingWebSocketHandler extends TextWebSocketHandler {

    private final ShipmentTrackingService trackingService;
    private final ObjectMapper objectMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            Long shipmentId = Long.parseLong(message.getPayload());
            System.out.println("Received shipment ID: " + shipmentId);
            ShipmentTrackingDTO dto = trackingService.getTrackingInfo(shipmentId);
            String json = objectMapper.writeValueAsString(dto);

            session.sendMessage(new TextMessage(json));
        } catch (Exception e) {
            session.sendMessage(new TextMessage("{\"error\": \"Invalid shipment ID or shipment not found\"}"));
        }
    }
}
