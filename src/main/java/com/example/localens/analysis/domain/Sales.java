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



    @Column(name = "20대남성")
    private String male20s;

    @Column(name = "20대여성")
    private String female20s;

    @Column(name = "30대남성")
    private String male30s;

    @Column(name = "30대여성")
    private String female30s;

    @Column(name = "40대남성")
    private String male40s;

    @Column(name = "40대여성")
    private String female40s;

    @Column(name = "50대남성")
    private String male50s;

    @Column(name = "50대여성")
    private String female50s;

    @Column(name = "60대이상남성")
    private String male60s;

    @Column(name = "60대이상여성")
    private String female60s;
}
