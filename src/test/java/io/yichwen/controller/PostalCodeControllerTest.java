package io.yichwen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Location;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostalCodeController.class)
@DisplayName("PostalCodeController Unit Testing")
public class PostalCodeControllerTest {

    @MockBean
    private PostalCodeService postalCodeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("To test update latitude and longitude by the given postal code")
    public void testToUpdateLatitudeLongitudeByTheGivenPostalCode() throws Exception {

        String postcode = "XX99 11GX";
        double latitude = 10.29722;
        double longitude = 20.90271;
        Coordinate coordinate = new Coordinate(latitude, longitude);

        Location location = Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .postalCode(postcode)
                .build();

        Mockito.when(postalCodeService.updatePostalCode(postcode, coordinate)).thenReturn(location);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(coordinate);

        mockMvc.perform(put("/postcode/" + postcode)
                .with(httpBasic("user", "user"))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode", Matchers.is(postcode)))
                .andExpect(jsonPath("$.latitude", Matchers.is(latitude)))
                .andExpect(jsonPath("$.longitude", Matchers.is(longitude)));
    }
}
