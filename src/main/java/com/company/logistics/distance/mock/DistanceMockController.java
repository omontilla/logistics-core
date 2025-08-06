package com.company.logistics.distance.mock;

import com.company.logistics.distance.client.dto.DistanceResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/mock/distance")
public class DistanceMockController {

    @GetMapping
    public DistanceResponse getDistance(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        int distanceKm = new Random().nextInt(450) + 50;
        double estimatedTimeHours = distanceKm / 60.0; // suponiendo 60km/h

        return new DistanceResponse(distanceKm, estimatedTimeHours);
    }
}