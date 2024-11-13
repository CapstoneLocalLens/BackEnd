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

    public Map<String, Double> getTimeZoneRatio() {
        // 1. 데이터 필터링: 2024년 4월 ~ 6월 데이터만 사용
        List<Hourly> filteredData = populationRepository.findAll().stream()
                .filter(data -> {
                    Long yearMonth = data.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406;
                })
                .collect(Collectors.toList());

        // 2. 전체 유동 인구의 총합 계산
        double totalPopulation = filteredData.stream()
                .mapToDouble(Hourly::getVisitPopulation)
                .sum();

        // 3. 시간대별 유동 인구 합계를 계산하고, 전체 유동 인구 대비 비율을 계산
        Map<String, Double> timeZoneRatio = filteredData.stream()
                .collect(Collectors.groupingBy(
                        data -> TimeZoneUtil.getTimeZone(Integer.parseInt(data.getTimeZone())), // 시간대를 구간으로 변환
                        Collectors.summingDouble(Hourly::getVisitPopulation)
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round((entry.getValue() / totalPopulation) * 1000) / 1.0 // 비율로 변환
                ));

        return timeZoneRatio;
    }
}