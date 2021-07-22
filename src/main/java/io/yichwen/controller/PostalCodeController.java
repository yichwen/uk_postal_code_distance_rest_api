package io.yichwen.controller;

import io.yichwen.dto.Coordinate;
import io.yichwen.dto.Location;
import io.yichwen.service.PostalCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postcode")
public class PostalCodeController {

    @Autowired
    private PostalCodeService postalCodeService;

    @PutMapping("/{postcode}")
    public Location updatePostcodeCoordinate(@PathVariable String postcode, @RequestBody Coordinate coordinate) {
        return postalCodeService.updatePostalCode(postcode, coordinate);
    }

}
