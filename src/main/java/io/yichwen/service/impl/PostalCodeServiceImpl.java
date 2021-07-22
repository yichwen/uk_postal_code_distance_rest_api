package io.yichwen.service.impl;

import io.yichwen.converter.PostalCodeLatitudeLongitudeToLocationConverter;
import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Location;
import io.yichwen.exception.PostalCodeNotFoundException;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import io.yichwen.respository.PostalCodeLatitudeLongitudeRepository;
import io.yichwen.service.PostalCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostalCodeServiceImpl implements PostalCodeService {

    private PostalCodeLatitudeLongitudeRepository postalCodeLatitudeLongitudeRepository;
    private PostalCodeLatitudeLongitudeToLocationConverter postalCodeLatitudeLongitudeToLocationConverter;

    @Override
    public Location updatePostalCode(String postcode, Coordinate coordinate) {
        PostalCodeLatitudeLongitude postcodeLatLng = postalCodeLatitudeLongitudeRepository.findByPostcode(postcode);
        if (postcodeLatLng == null) {
            throw new PostalCodeNotFoundException(postcode);
        }

        postcodeLatLng.setLatitude(coordinate.getLatitude());
        postcodeLatLng.setLongitude(coordinate.getLongitude());

        postcodeLatLng = postalCodeLatitudeLongitudeRepository.save(postcodeLatLng);
        return postalCodeLatitudeLongitudeToLocationConverter.convert(postcodeLatLng);
    }

}
