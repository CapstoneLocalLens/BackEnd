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


}

