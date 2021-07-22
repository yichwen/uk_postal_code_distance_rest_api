package io.yichwen.service.impl;

import io.yichwen.service.HaversineService;
import org.springframework.stereotype.Service;

/**
 * This service provides the actual implementation to calculate the distance between two points on the planet
 * by the given longitude/latitude pairs (in degrees)
 */
@Service
public class HaversineServiceImpl implements HaversineService {

    private final static double EARTH_RADIUS = 6371;

    @Override
    public double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }

}
