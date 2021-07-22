package io.yichwen.controller;

import io.yichwen.dto.PostalCodeDistance;
import io.yichwen.service.PostalCodeDistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distance")
public class PostalCodeDistanceController {

    @Autowired
    private PostalCodeDistanceService postalCodeDistanceService;

    @GetMapping
    public PostalCodeDistance getDistance(@RequestParam("postcode1") String postcode1,
                                          @RequestParam("postcode2") String postcode2) {
        return postalCodeDistanceService.getDistance(postcode1, postcode2);
    }

}
