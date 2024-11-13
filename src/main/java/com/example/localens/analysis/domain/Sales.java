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

    @Column(name = "06~09시")
    private String time0609;

    @Column(name = "09~12시")
    private String time0912;

    @Column(name = "12~15시")
    private String time1215;

    @Column(name = "15~18시")
    private String time1518;

    @Column(name = "18~21시")
    private String time1821;

    @Column(name = "21~24시")
    private String time2124;

    @Column(name = "24~06시")
    private String time2406;
}
