package com.company.logistics.distance.client;

import com.company.logistics.distance.client.dto.DistanceResponse;

public interface DistanceApiClient {
    DistanceResponse getDistance(String origin, String destination);
}
