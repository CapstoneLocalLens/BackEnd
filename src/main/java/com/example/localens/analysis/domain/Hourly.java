package com.example.localens.analysis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "hourly")
@NoArgsConstructor
@Getter
public class Hourly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SEX_DV_CD") // 성별 컬럼 매핑
    private String gender;

    @Column(name = "AGE_GROUP") // 연령대 컬럼 매핑
    private String ageGroup;

    @Column(name = "VISIT_POPL") // 유동 인구 컬럼 매핑
    private double visitPopulation;

    @Column(name = "P_YYYYMM") // 연도와 월을 나타내는 컬럼 매핑
    private Long yearMonth;

    @Column(name = "TMZN") // 시간대 컬럼 매핑
    private Long timeZone;


}