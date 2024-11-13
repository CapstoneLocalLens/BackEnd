package com.example.localens.analysis.controller;

import com.example.localens.analysis.service.GenderAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/population")
public class PopulationController {

    @Autowired
    private GenderAge genderAge;


    @GetMapping("/gender-age-ratio")
    public Map<String, Map<String, Double>> getGenderAgeRatio() {
        return genderAge.getGenderAgeRatios();
    }

}
