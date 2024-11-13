package com.example.localens.analysis.controller;

import com.example.localens.analysis.service.DowSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private DowSales dowSales;

    @GetMapping("/weekday-ratio/{place}")
    public ResponseEntity<Map<String, Double>> getDailySalesRatio(@PathVariable String place) {
        Map<String, Double> salesRatio = dowSales.calculateDailySalesRatio(place);
        return ResponseEntity.ok(salesRatio);
    }
}
