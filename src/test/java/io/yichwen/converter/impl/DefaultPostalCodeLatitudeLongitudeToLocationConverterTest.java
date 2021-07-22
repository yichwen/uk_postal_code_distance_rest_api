package io.yichwen.converter.impl;

import io.yichwen.dto.Distance;
import io.yichwen.dto.Location;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DefaultPostalCodeLatitudeLongitudeToLocationConverter Unit Testing")
public class DefaultPostalCodeLatitudeLongitudeToLocationConverterTest {

    private final static double LONGITUDE = -2.130018000;
    private final static double LATITUDE = 57.064273000;
    private final static String POSTCODE = "XX11 9GG";

    @Test
    @DisplayName("To convert PostalCodeLatitudeLongitude object to Location object")
    public void shouldSuccessToConvertPostalCodeLatitudeLongitudeToLocation() {
        PostalCodeLatitudeLongitude postalCodeLatLng = new PostalCodeLatitudeLongitude();
        postalCodeLatLng.setLatitude(LATITUDE);
        postalCodeLatLng.setLongitude(LONGITUDE);
        postalCodeLatLng.setPostcode(POSTCODE);
        DefaultPostalCodeLatitudeLongitudeToLocationConverter converter = new DefaultPostalCodeLatitudeLongitudeToLocationConverter();
        Location location = converter.convert(postalCodeLatLng);

        assertEquals(LATITUDE, location.getLatitude(), 0.0);
        assertEquals(LONGITUDE, location.getLongitude(), 0.0);
        assertEquals(POSTCODE, location.getPostalCode());
    }

}
