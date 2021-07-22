package io.yichwen.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private String postalCode;
    private Double latitude;
    private Double longitude;
}
