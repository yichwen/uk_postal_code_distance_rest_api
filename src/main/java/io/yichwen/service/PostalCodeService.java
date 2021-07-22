package io.yichwen.service;

import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Location;

public interface PostalCodeService {
    Location updatePostalCode(String postcode, Coordinate coordinate);
}
