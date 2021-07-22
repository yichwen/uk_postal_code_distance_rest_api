package io.yichwen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Distance;
import io.yichwen.dto.Location;
import io.yichwen.dto.PostalCodeDistance;
import io.yichwen.service.PostalCodeDistanceService;
import io.yichwen.service.PostalCodeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostalCodeDistanceController.class)
@DisplayName("PostalCodeController Unit Testing")
public class PostalCodeDistanceControllerTest {

    @MockBean
    private PostalCodeDistanceService postalCodeDistanceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("To test get distance by given two postal codes")
    public void testToGetDistanceByGivenTwoPostalCodes() throws Exception {

        String postcode1 = "XX99 11GX";
        double latitude1 = -20.245345;
        double longitude1 = 10.325345;

        String postcode2 = "XX88 10NY";
        double latitude2 = 34.56546;
        double longitude2 = -24.35656;

        double distanceValue = 50.2344;

        PostalCodeDistance distance = PostalCodeDistance.builder()
                .location1(Location.builder()
                        .longitude(longitude1)
                        .latitude(latitude1)
                        .postalCode(postcode1)
                        .build())
                .location2(Location.builder()
                        .longitude(longitude2)
                        .latitude(latitude2)
                        .postalCode(postcode2)
                        .build())
                .distance(Distance.builder().value(distanceValue).build())
                .build();

        Mockito.when(postalCodeDistanceService.getDistance(postcode1, postcode2)).thenReturn(distance);

        mockMvc.perform(get("/distance")
                .param("postcode1", postcode1)
                .param("postcode2", postcode2)
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distance.value", is(distanceValue)))
                .andExpect(jsonPath("$.distance.unit", is("KM")))
                .andExpect(jsonPath("$.location1.longitude", is(longitude1)))
                .andExpect(jsonPath("$.location1.latitude", is(latitude1)))
                .andExpect(jsonPath("$.location1.postalCode", is(postcode1)))
                .andExpect(jsonPath("$.location2.longitude", is(longitude2)))
                .andExpect(jsonPath("$.location2.latitude", is(latitude2)))
                .andExpect(jsonPath("$.location2.postalCode", is(postcode2)));
    }

}
