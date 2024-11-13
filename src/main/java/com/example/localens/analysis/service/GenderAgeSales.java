package com.example.localens.analysis.service;

import com.example.localens.analysis.domain.Sales;
import com.example.localens.analysis.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenderAgeSales {

    @Autowired
    private SalesRepository salesRepository;

    public Map<String, Map<String, Double>> calculateGenderAgeSalesRatio(String place) {
        List<Sales> filteredSalesList = salesRepository.findByPlace(place).stream()
                .filter(sales -> {
                    Long yearMonth = sales.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406;
                })
                .collect(Collectors.toList());

        Map<String, Long> maleSalesSum = new HashMap<>();
        Map<String, Long> femaleSalesSum = new HashMap<>();
        long totalMaleSales = 0;
        long totalFemaleSales = 0;

        // 각 연령대별 남성/여성 매출 합계를 저장할 맵 초기화
        String[] ageGroups = {"20대", "30대", "40대", "50대", "60대이상"};
        for (String ageGroup : ageGroups) {
            maleSalesSum.put(ageGroup, 0L);
            femaleSalesSum.put(ageGroup, 0L);
        }

        // 연령대별 매출을 누적하여 합산
        for (Sales sales : filteredSalesList) {
            maleSalesSum.put("20대", maleSalesSum.get("20대") + Long.parseLong(sales.getMale20s().replace(",", "").trim()));
            femaleSalesSum.put("20대", femaleSalesSum.get("20대") + Long.parseLong(sales.getFemale20s().replace(",", "").trim()));
            maleSalesSum.put("30대", maleSalesSum.get("30대") + Long.parseLong(sales.getMale30s().replace(",", "").trim()));
            femaleSalesSum.put("30대", femaleSalesSum.get("30대") + Long.parseLong(sales.getFemale30s().replace(",", "").trim()));
            maleSalesSum.put("40대", maleSalesSum.get("40대") + Long.parseLong(sales.getMale40s().replace(",", "").trim()));
            femaleSalesSum.put("40대", femaleSalesSum.get("40대") + Long.parseLong(sales.getFemale40s().replace(",", "").trim()));
            maleSalesSum.put("50대", maleSalesSum.get("50대") + Long.parseLong(sales.getMale50s().replace(",", "").trim()));
            femaleSalesSum.put("50대", femaleSalesSum.get("50대") + Long.parseLong(sales.getFemale50s().replace(",", "").trim()));
            maleSalesSum.put("60대이상", maleSalesSum.get("60대이상") + Long.parseLong(sales.getMale60s().replace(",", "").trim()));
            femaleSalesSum.put("60대이상", femaleSalesSum.get("60대이상") + Long.parseLong(sales.getFemale60s().replace(",", "").trim()));
        }

        // 총 남성 매출과 여성 매출 합계를 계산
        totalMaleSales = maleSalesSum.values().stream().mapToLong(Long::longValue).sum();
        totalFemaleSales = femaleSalesSum.values().stream().mapToLong(Long::longValue).sum();

        // 연령대별로 남성과 여성의 매출 비율을 계산하여 반환할 맵 생성
        Map<String, Map<String, Double>> salesRatio = new HashMap<>();

        for (String ageGroup : ageGroups) {
            Map<String, Double> genderRatio = new HashMap<>();
            genderRatio.put("남성", Math.round((maleSalesSum.get(ageGroup) * 1000.0) / totalMaleSales) / 10.0);
            genderRatio.put("여성", Math.round((femaleSalesSum.get(ageGroup) * 1000.0) / totalFemaleSales) / 10.0);
            salesRatio.put(ageGroup, genderRatio);
        }

        return salesRatio;
    }
}
