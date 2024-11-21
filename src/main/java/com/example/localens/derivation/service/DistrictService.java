package com.example.localens.derivation.service;

import com.example.localens.derivation.repository.DistrictMetricsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    private final DistrictMetricsRepository districtMetricsRepository;

    public DistrictService(DistrictMetricsRepository districtMetricsRepository) {
        this.districtMetricsRepository = districtMetricsRepository;
    }

    // 혼잡도 변화율 평균값 계산
    public double calculateAverageCongestionChangeRate(String districtName, String startDate, String endDate) {
        List<Double> congestionRates = districtMetricsRepository.findByCommercialDistrict_DistrictNameAndMetricDateBetween(
                districtName, startDate, endDate
        );

        return congestionRates.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0); // 데이터가 없을 경우 0.0 반환
    }
}

