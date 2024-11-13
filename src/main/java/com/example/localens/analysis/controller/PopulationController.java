package com.example.localens.analysis.controller;

import com.example.localens.analysis.service.DayOfWeekFlow;
import com.example.localens.analysis.service.GenderAge;
import com.example.localens.analysis.service.TimeZoneFlow;
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

    @Autowired
    private TimeZoneFlow timeZoneFlow;

    @Autowired
    private DayOfWeekFlow dayOfWeekFlow;


    @GetMapping("/gender-age-ratio")
    public Map<String, Map<String, Double>> getGenderAgeRatio() {
        return genderAge.getGenderAgeRatios();
    }

    @GetMapping("/time-zone-ratio")
    public Map<String, Double> getTimeZoneRatio() {
        return timeZoneFlow.getTimeZoneRatio();
    }

    @GetMapping("/weekday-ratio")
    public Map<String, Double> getWeekdayPopulationRatio() {
        return dayOfWeekFlow.getWeekdayPopulationRatio();
    }

}
