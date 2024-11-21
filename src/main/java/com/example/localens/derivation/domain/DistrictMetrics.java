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
<<<<<<< HEAD
    @Column(name = "feature_uuid")
    private UUID featureUuid;

    @Column(name = "metric_date")
    private String metricDate;

    @Column(name = "congestion_change_rate")
    private Double congestionChangeRate;

    @Column(name = "population_avg", nullable = false)
    private Double populationAvg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_uuid")
=======
    @Column(name = "feature_uuid", nullable = false)
    private UUID featureUuid;

    @Column(name = "metric_date", nullable = false)
    private String metricDate;

    @Column(name = "congestion_change_rate", nullable = false)
    private Double congestionChangeRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_uuid", nullable = false)
>>>>>>> acb3bfe37c4e01a70d61b1516934b9dc9460a47b
    private CommercialDistrict commercialDistrict;

}

