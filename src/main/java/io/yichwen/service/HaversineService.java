package io.yichwen.service;

public interface HaversineService {
    double calculateDistance(double latitude, double longitude, double latitude2, double longitude2);
}
