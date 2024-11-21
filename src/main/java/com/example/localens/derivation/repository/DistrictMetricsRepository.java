package com.example.localens.derivation.repository;

import com.example.localens.derivation.domain.DistrictMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DistrictMetricsRepository extends JpaRepository<DistrictMetrics, UUID> {

    // 특정 상권 이름과 날짜 범위 -> congestion_change_rate 조회
    List<Double> findCongestionChangeRateByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

    // 특정 상권 이름과 날짜 범위 -> 유동인구 수(population_avg) 조회
    List<Double> findPopulationAvgByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

    // 체류/방문 비율 조회
    List<Double> findStayToVisitRatioByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

    // 체류시간 대비 방문자 수 조회
    List<Double> findAverageStayTimePerVisitorByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

    // 방문 집중도 조회
    List<Double> findVisitConcentrationByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

    // 평균 체류시간 변화율 조회
    List<Double> findAverageStayTimeChangeRateByCommercialDistrict_DistrictNameAndMetricDateBetween(
            String districtName, String startDate, String endDate
    );

}

