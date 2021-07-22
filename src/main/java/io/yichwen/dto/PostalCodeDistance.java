package io.yichwen.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalCodeDistance {
    private Location location1;
    private Location location2;
    private Distance distance;
}
