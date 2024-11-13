package com.example.localens.analysis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@Getter
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "상권명")
    private String place;

    @Column(name = "기준년월")
    private Long yearMonth;

    @Column(name = "월요일")
    private String mon;

    @Column(name = "화요일")
    private String tue;

    @Column(name = "수요일")
    private String wed;

    @Column(name = "목요일")
    private String thu;

    @Column(name = "금요일")
    private String fri;

    @Column(name = "토요일")
    private String sat;

    @Column(name = "일요일")
    private String sun;

}
