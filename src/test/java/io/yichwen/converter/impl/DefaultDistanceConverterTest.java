package io.yichwen.converter.impl;

import io.yichwen.dto.Distance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DefaultDistanceConverter Unit Testing")
public class DefaultDistanceConverterTest {

    @Test
    @DisplayName("To convert distance value to Distance object")
    public void shouldSuccessToConvertTheDistance() {
        Double expected = 1234.55;
        DefaultDistanceConverter converter = new DefaultDistanceConverter();
        Distance distance = converter.convert(expected);
        assertEquals(distance.getValue(), expected, 0.0);
        assertEquals(distance.getUnit(), "KM");
    }

}
