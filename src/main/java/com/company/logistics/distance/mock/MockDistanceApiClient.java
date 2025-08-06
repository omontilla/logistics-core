package com.company.logistics.distance.mock;

import com.company.logistics.distance.client.DistanceApiClient;
import com.company.logistics.distance.client.dto.DistanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockDistanceApiClient implements DistanceApiClient {

    private final RestTemplate restTemplate;

    @Value("${distance.api.url}")
    private String baseUrl;

    @Override
    public DistanceResponse getDistance(String origin, String destination) {
        String url = String.format("%s?origin=%s&destination=%s", baseUrl, origin, destination);
        log.debug("Calling distance API with URL: {}", url);
        try {
            DistanceResponse response = restTemplate.getForObject(url, DistanceResponse.class);
            log.debug("Response received: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error calling distance API: {}", e.getMessage(), e);
            throw e;
        }
    }
}