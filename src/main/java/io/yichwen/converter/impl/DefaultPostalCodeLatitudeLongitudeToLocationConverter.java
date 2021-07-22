package io.yichwen.converter.impl;

import io.yichwen.converter.PostalCodeLatitudeLongitudeToLocationConverter;
import io.yichwen.dto.Location;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import org.springframework.stereotype.Component;

@Component
public class DefaultPostalCodeLatitudeLongitudeToLocationConverter implements PostalCodeLatitudeLongitudeToLocationConverter {

    @Override
    public Location convert(PostalCodeLatitudeLongitude from) {
        if (from != null) {
            return Location.builder()
                    .latitude(from.getLatitude())
                    .longitude(from.getLongitude())
                    .postalCode(from.getPostcode())
                    .build();
        } else {
            return Location.builder().build();
        }
    }

}
