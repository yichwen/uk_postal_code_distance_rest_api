package io.yichwen.service.impl;

import io.yichwen.converter.DistanceConverter;
import io.yichwen.converter.PostalCodeLatitudeLongitudeToLocationConverter;
import io.yichwen.dto.Distance;
import io.yichwen.dto.Location;
import io.yichwen.dto.PostalCodeDistance;
import io.yichwen.exception.PostalCodeNotFoundException;
import io.yichwen.model.PostalCodeLatitudeLongitude;
import io.yichwen.respository.PostalCodeLatitudeLongitudeRepository;
import io.yichwen.service.HaversineService;
import io.yichwen.service.PostalCodeDistanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("PostalCodeDistanceServiceImpl Unit Testing")
public class PostalCodeDistanceServiceImplTest {

    private final static PostalCodeLatitudeLongitude POSTAL_CODE_LATITUDE_LONGITUDE_1 = new PostalCodeLatitudeLongitude(
            1L, "XX11 9GG", 57.064273000, -2.130018000
    );

    private final static PostalCodeLatitudeLongitude POSTAL_CODE_LATITUDE_LONGITUDE_2 = new PostalCodeLatitudeLongitude(
            2L, "XX22 9GG", -57.064273000, 2.130018000
    );

    private final static double EXPECTED_KM = 554.54354;

    private static PostalCodeDistanceService postalCodeDistanceService;

    @BeforeAll
    public static void setUp() {

        PostalCodeLatitudeLongitudeRepository postalCodeLatitudeLongitudeRepository = mock(PostalCodeLatitudeLongitudeRepository.class);
        // mock findByPostcode() method
        when(postalCodeLatitudeLongitudeRepository.findByPostcode(POSTAL_CODE_LATITUDE_LONGITUDE_1.getPostcode()))
                .thenReturn(POSTAL_CODE_LATITUDE_LONGITUDE_1);
        when(postalCodeLatitudeLongitudeRepository.findByPostcode(POSTAL_CODE_LATITUDE_LONGITUDE_2.getPostcode()))
                .thenReturn(POSTAL_CODE_LATITUDE_LONGITUDE_2);

        // mock the location converter
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

        // mock the distance converter
        Distance distance = Distance.builder().value(EXPECTED_KM).build();
        DistanceConverter distanceConverter = mock(DistanceConverter.class);
        when(distanceConverter.convert(EXPECTED_KM)).thenReturn(distance);

        // mock HaversineService
        HaversineService haversineService = mock(HaversineService.class);
        when(haversineService.calculateDistance(
                POSTAL_CODE_LATITUDE_LONGITUDE_1.getLatitude(),
                POSTAL_CODE_LATITUDE_LONGITUDE_1.getLongitude(),
                POSTAL_CODE_LATITUDE_LONGITUDE_2.getLatitude(),
                POSTAL_CODE_LATITUDE_LONGITUDE_2.getLongitude())).thenReturn(EXPECTED_KM);

        postalCodeDistanceService = new PostalCodeDistanceServiceImpl(
                haversineService,
                postalCodeLatitudeLongitudeRepository,
                distanceConverter,
                postalCodeLatitudeLongitudeToLocationConverter
        );
    }

    @Test
    @DisplayName("Should get the distance between the postal codes successfully")
    public void shouldSuccessToGetTheDistanceBetweenThePostalCodes() {
        PostalCodeDistance postalCodeDistance = postalCodeDistanceService.getDistance(
                POSTAL_CODE_LATITUDE_LONGITUDE_1.getPostcode(),
                POSTAL_CODE_LATITUDE_LONGITUDE_2.getPostcode()
        );
        assertEquals(EXPECTED_KM, postalCodeDistance.getDistance().getValue());
        assertEquals("KM", postalCodeDistance.getDistance().getUnit());
        assertLocation(POSTAL_CODE_LATITUDE_LONGITUDE_1, postalCodeDistance.getLocation1());
        assertLocation(POSTAL_CODE_LATITUDE_LONGITUDE_2, postalCodeDistance.getLocation2());
    }

    private void assertLocation(PostalCodeLatitudeLongitude postalCodeLatLng, Location location) {
        assertEquals(postalCodeLatLng.getLatitude(), location.getLatitude(), 0.0);
        assertEquals(postalCodeLatLng.getLongitude(), location.getLongitude(), 0.0);
        assertEquals(postalCodeLatLng.getPostcode(), location.getPostalCode());
    }

    @Test
    @DisplayName("Should throw the exception if the postal code 1 was not found")
    public void shouldFailedToGetDistanceIfPostcode1IsNotFound() {
        Assertions.assertThrows(PostalCodeNotFoundException.class, () -> {
            PostalCodeDistance postalCodeDistance = postalCodeDistanceService.getDistance(
                    "UNKNOWN", POSTAL_CODE_LATITUDE_LONGITUDE_2.getPostcode()
            );
        });
    }

    @Test
    @DisplayName("Should throw the exception if the postal code 2 was not found")
    public void shouldFailedToGetDistanceIfPostcode2IsNotFound() {
        Assertions.assertThrows(PostalCodeNotFoundException.class, () -> {
            PostalCodeDistance postalCodeDistance = postalCodeDistanceService.getDistance(
                    POSTAL_CODE_LATITUDE_LONGITUDE_1.getPostcode(), "UNKNOWN"
            );
        });
    }
}
