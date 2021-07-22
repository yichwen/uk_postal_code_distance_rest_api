package io.yichwen.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Distance {
    private Double value;
    private final String unit = "KM";
}
