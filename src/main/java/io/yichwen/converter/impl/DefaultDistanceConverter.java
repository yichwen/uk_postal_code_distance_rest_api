package io.yichwen.converter.impl;

import io.yichwen.converter.DistanceConverter;
import io.yichwen.dto.Distance;
import org.springframework.stereotype.Component;

@Component
public class DefaultDistanceConverter implements DistanceConverter {

    @Override
    public Distance convert(Double from) {
        return Distance.builder().value(from).build();
    }

}
