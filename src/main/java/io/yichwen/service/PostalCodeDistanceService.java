package io.yichwen.service;

import io.yichwen.dto.PostalCodeDistance;

public interface PostalCodeDistanceService {
    PostalCodeDistance getDistance(String postcode1, String postcode2);
}
