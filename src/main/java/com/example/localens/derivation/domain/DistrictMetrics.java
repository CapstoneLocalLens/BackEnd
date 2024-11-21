package com.example.localens.derivation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "district_metrics")
@NoArgsConstructor
@Getter
public class DistrictMetrics {

    @Id
    @Column(name = "feature_uuid")
    private UUID featureUuid;

    @Column(name = "metric_date")
    private String metricDate;

    @Column(name = "congestion_change_rate")
    private Double congestionChangeRate;

    @Column(name = "population_avg")
    private Double populationAvg;

    @Column(name = "stay_to_visit_ratio", nullable = false)
    private Double stayToVisitRatio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_uuid")
    private CommercialDistrict commercialDistrict;

}

