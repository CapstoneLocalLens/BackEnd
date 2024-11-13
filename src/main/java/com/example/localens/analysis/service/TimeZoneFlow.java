package com.example.localens.analysis.service;

import com.example.localens.analysis.domain.Hourly;
import com.example.localens.analysis.repository.PopulationRepository;
import com.example.localens.analysis.util.TimeZoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeZoneFlow {

    @Autowired
    private PopulationRepository populationRepository;

    public Map<String, Double> getTimeZoneRatio(String place) {
        // 1. 데이터 필터링: 2024년 4월 ~ 6월 데이터만 사용
        List<Hourly> filteredData = populationRepository.findAll().stream()
                .filter(data -> {
                    Long yearMonth = data.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406 && data.getPlace().equals(place);
                })
                .collect(Collectors.toList());

        // 2. 전체 유동 인구의 총합 계산
        double totalPopulation = filteredData.stream()
                .mapToDouble(Hourly::getVisitPopulation)
                .sum();

        // 3. 시간대별 유동 인구 합계를 계산하고, 전체 유동 인구 대비 비율을 계산
        Map<String, Double> timeZoneRatio = filteredData.stream()
                .collect(Collectors.groupingBy(
                        data -> TimeZoneUtil.getTimeZone(data.getTimeZone().intValue()), // 시간대를 구간으로 변환
                        Collectors.summingDouble(Hourly::getVisitPopulation)
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round((entry.getValue() / totalPopulation) * 1000) / 10.0 // 비율로 변환
                ));

        return timeZoneRatio;
    }

    public Map<String, Map<String, Double>> getTimeZonePopulationRatio(String place) {
        // 1. 데이터 필터링: 2024년 4월 ~ 6월 데이터만 사용 및 상권명으로 필터링
        List<Hourly> filteredData = populationRepository.findAll().stream()
                .filter(data -> {
                    Long yearMonth = data.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406 && data.getPlace().equalsIgnoreCase(place);
                })
                .collect(Collectors.toList());

        // 2. 전체 유입 인구 수, 머물고 있는 인구 수, 전부터 머물고 있는 인구 수 계산
        double totalVisitMinPopulation = filteredData.stream()
                .mapToDouble(hourly -> hourly.getVisitMinPopulation() != null ? hourly.getVisitMinPopulation() : 0.0)
                .sum();
        double totalVisitPopulation = filteredData.stream()
                .mapToDouble(hourly -> hourly.getVisitPopulation() != null ? hourly.getVisitPopulation() : 0.0)
                .sum();
        double totalPreviouslyStayedPopulation = totalVisitPopulation - totalVisitMinPopulation;

        // 3. 시간대별 유입 인구 수, 전부터 머물고 있는 인구 수, 유동 인구 비율 계산
        Map<String, Map<String, Double>> timeZonePopulationRatio = filteredData.stream()
                .collect(Collectors.groupingBy(
                        data -> TimeZoneUtil.getTimeZone(data.getTimeZone().intValue()),
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            double visitMinPopulation = list.stream()
                                    .mapToDouble(hourly -> hourly.getVisitMinPopulation() != null ? hourly.getVisitMinPopulation() : 0.0)
                                    .sum();
                            double visitPopulation = list.stream()
                                    .mapToDouble(hourly -> hourly.getVisitPopulation() != null ? hourly.getVisitPopulation() : 0.0)
                                    .sum();
                            double previouslyStayedPopulation = visitPopulation - visitMinPopulation;
                            double total = visitPopulation;
                            return Map.of(
                                    "visitMinPopulationRatio", total > 0 ? Math.round((visitMinPopulation / total) * 1000) / 10.0 : 0.0,
                                    "previouslyStayedPopulationRatio", total > 0 ? Math.round((previouslyStayedPopulation / total) * 1000) / 10.0 : 0.0,
                                    "totalPopulationRatio", total > 0 ? Math.round((visitPopulation / totalVisitPopulation) * 1000) / 10.0 : 0.0
                            );
                        })
                ));

        return timeZonePopulationRatio;
    }
}