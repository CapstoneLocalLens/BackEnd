package com.example.localens.analysis.controller;

import com.example.localens.analysis.service.DayOfWeekFlow;
import com.example.localens.analysis.service.GenderAge;
import com.example.localens.analysis.service.TimeZoneFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/gender-age-ratio/{place}")
    public Map<String, Map<String, Double>> getGenderAgeRatio(@PathVariable String place) {
        return genderAge.getGenderAgeRatios(place);
    }

    @GetMapping("/time-zone-ratio/{place}")
    public Map<String, Double> getTimeZoneRatio(@PathVariable String place) {
        return timeZoneFlow.getTimeZoneRatio(place);
    }

    @GetMapping("/weekday-ratio/{place}")
    public Map<String, Double> getWeekdayPopulationRatio(@PathVariable String place) {
        return dayOfWeekFlow.getWeekdayPopulationRatio(place);
    }

}
