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
    @Column(name = "feature_uuid", nullable = false)
    private UUID featureUuid;

    @Column(name = "metric_date", nullable = false)
    private String metricDate;

    @Column(name = "congestion_change_rate", nullable = false)
    private Double congestionChangeRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_uuid", nullable = false)
    private CommercialDistrict commercialDistrict;

}

