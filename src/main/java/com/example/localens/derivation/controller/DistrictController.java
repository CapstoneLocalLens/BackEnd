package com.example.localens.derivation.controller;

import com.example.localens.derivation.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/average-congestion/{districtName}")
    public ResponseEntity<Double> getAverageCongestionChangeRate(@PathVariable String districtName) {
        double averageRate = districtService.calculateAverageCongestionChangeRate(districtName, "202309", "202408");
        return ResponseEntity.ok(averageRate);
    }
}

