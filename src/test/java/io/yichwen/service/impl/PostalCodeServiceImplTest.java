package io.yichwen.service.impl;

import io.yichwen.converter.PostalCodeLatitudeLongitudeToLocationConverter;
import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Location;
import io.yichwen.exception.PostalCodeNotFoundException;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import io.yichwen.respository.PostalCodeLatitudeLongitudeRepository;
import io.yichwen.service.PostalCodeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("PostalCodeServiceImpl Unit Testing")
public class PostalCodeServiceImplTest {

    private final static Long ID = 1L;
    private final static double LONGITUDE = -2.130018000;
    private final static double LATITUDE = 57.064273000;
    private final static String POSTCODE = "XX11 9GG";

    private final static double UPDATED_LONGITUDE = 2.130018000;
    private final static double UPDATED_LATITUDE = -57.064273000;

    private static PostalCodeService postalCodeService;

    @BeforeAll
    public static void setUp() {

        PostalCodeLatitudeLongitude postalCodeLatLng = new PostalCodeLatitudeLongitude();
        postalCodeLatLng.setId(ID);
        postalCodeLatLng.setPostcode(POSTCODE);
        postalCodeLatLng.setLongitude(LONGITUDE);
        postalCodeLatLng.setLatitude(LATITUDE);

        PostalCodeLatitudeLongitudeRepository postalCodeLatitudeLongitudeRepository = mock(PostalCodeLatitudeLongitudeRepository.class);
        // mock findByPostcode() method
        when(postalCodeLatitudeLongitudeRepository.findByPostcode(POSTCODE)).thenReturn(postalCodeLatLng);
        // mock save() method to return what is saved
        when(postalCodeLatitudeLongitudeRepository.save(any(PostalCodeLatitudeLongitude.class)))
                .thenAnswer((Answer) invocation -> invocation.getArguments()[0]);

        // mock the converter
        PostalCodeLatitudeLongitudeToLocationConverter postalCodeLatitudeLongitudeToLocationConverter = mock(PostalCodeLatitudeLongitudeToLocationConverter.class);
        when(postalCodeLatitudeLongitudeToLocationConverter.convert(any(PostalCodeLatitudeLongitude.class)))
                .thenAnswer((Answer) invocation -> {
                    PostalCodeLatitudeLongitude postcodeLatLng = (PostalCodeLatitudeLongitude) invocation.getArguments()[0];
                    return Location.builder()
                            .postalCode(postcodeLatLng.getPostcode())
                            .latitude(postcodeLatLng.getLatitude())
                            .longitude(postcodeLatLng.getLongitude())
                            .build();
                });

        postalCodeService = new PostalCodeServiceImpl(postalCodeLatitudeLongitudeRepository, postalCodeLatitudeLongitudeToLocationConverter);
    }

    @Test
    @DisplayName("Should update latitude and longitude for the postal code successfully")
    public void shouldSuccessToUpdateLatitudeAndLongitudeForPostcode() {
        Location location = postalCodeService.updatePostalCode(POSTCODE, new Coordinate(UPDATED_LATITUDE, UPDATED_LONGITUDE));
        assertEquals(UPDATED_LATITUDE, location.getLatitude(), 0.0);
        assertEquals(UPDATED_LONGITUDE, location.getLongitude(), 0.0);
        assertEquals(POSTCODE, location.getPostalCode());
    }

    @Test
    @DisplayName("Should throw the exception if the postal code was not found")
    public void shouldFailedToUpdateIfPostcodeIsNotFound() {
        Assertions.assertThrows(PostalCodeNotFoundException.class, () -> {
            postalCodeService.updatePostalCode("UNKNOWN", new Coordinate(UPDATED_LATITUDE, UPDATED_LONGITUDE));
        });
    }

}
