package io.yichwen.service.impl;

import io.yichwen.converter.DistanceConverter;
import io.yichwen.converter.PostalCodeLatitudeLongitudeToLocationConverter;
import io.yichwen.dto.Location;
import io.yichwen.dto.PostalCodeDistance;
import io.yichwen.exception.PostalCodeNotFoundException;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import io.yichwen.respository.PostalCodeLatitudeLongitudeRepository;
import io.yichwen.service.HaversineService;
import io.yichwen.service.PostalCodeDistanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostalCodeDistanceServiceImpl implements PostalCodeDistanceService {

    private HaversineService haversineService;
    private PostalCodeLatitudeLongitudeRepository postalCodeLatitudeLongitudeRepository;
    private DistanceConverter distanceConverter;
    private PostalCodeLatitudeLongitudeToLocationConverter postalCodeLatitudeLongitudeToLocationConverter;

    @Override
    public PostalCodeDistance getDistance(String postcode1, String postcode2) {

        PostalCodeLatitudeLongitude postcodeLatLng1 = postalCodeLatitudeLongitudeRepository.findByPostcode(postcode1);
        if (postcodeLatLng1 == null) {
            throw new PostalCodeNotFoundException(postcode1);
        }

        PostalCodeLatitudeLongitude postcodeLatLng2 = postalCodeLatitudeLongitudeRepository.findByPostcode(postcode2);
        if (postcodeLatLng2 == null) {
            throw new PostalCodeNotFoundException(postcode2);
        }

        double distance = haversineService.calculateDistance(
                                postcodeLatLng1.getLatitude(),
                                postcodeLatLng1.getLongitude(),
                                postcodeLatLng2.getLatitude(),
                                postcodeLatLng2.getLongitude());

        PostalCodeDistance postalCodeDistance = PostalCodeDistance.builder()
                .distance(distanceConverter.convert(distance))
                .location1(postalCodeLatitudeLongitudeToLocationConverter.convert(postcodeLatLng1))
                .location2(postalCodeLatitudeLongitudeToLocationConverter.convert(postcodeLatLng2))
                .build();

        return postalCodeDistance;
    }

}
