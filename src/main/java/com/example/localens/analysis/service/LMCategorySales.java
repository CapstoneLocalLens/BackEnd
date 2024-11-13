package com.example.localens.analysis.service;

import com.example.localens.analysis.domain.Sales;
import com.example.localens.analysis.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LMCategorySales {

    @Autowired
    private SalesRepository salesRepository;

    public Map<String, Map<String, Double>> calculateCategorySalesRatioByPlace(String place) {
        // 주어진 상권에 해당하는 매출 데이터 조회
        List<Sales> salesList = salesRepository.findByPlace(place);

        // 대분류업종별 총 매출과 중분류업종 매출을 저장할 Map 초기화
        Map<String, Long> largeCategorySalesSum = new HashMap<>();
        Map<String, Map<String, Long>> mediumCategorySalesSum = new HashMap<>();

        // 매출 데이터를 기반으로 대분류 및 중분류 매출 누적
        for (Sales sales : salesList) {
            String largeCategory = sales.getLargeCategory();
            String mediumCategory = sales.getMediumCategory();
            Long salesAmount = Long.parseLong(sales.getSalesAmount().replace(",", "").trim());

            // 대분류업종별 매출 합산
            largeCategorySalesSum.put(largeCategory, largeCategorySalesSum.getOrDefault(largeCategory, 0L) + salesAmount);

            // 대분류업종 내 중분류업종별 매출 합산
            mediumCategorySalesSum.putIfAbsent(largeCategory, new HashMap<>());
            Map<String, Long> mediumSales = mediumCategorySalesSum.get(largeCategory);
            mediumSales.put(mediumCategory, mediumSales.getOrDefault(mediumCategory, 0L) + salesAmount);
        }

        // 각 대분류업종별로 중분류업종 매출 비율 계산
        Map<String, Map<String, Double>> categorySalesRatio = new HashMap<>();

        for (String largeCategory : largeCategorySalesSum.keySet()) {
            long totalLargeCategorySales = largeCategorySalesSum.get(largeCategory);
            Map<String, Double> mediumSalesRatio = new HashMap<>();

            for (Map.Entry<String, Long> entry : mediumCategorySalesSum.get(largeCategory).entrySet()) {
                String mediumCategory = entry.getKey();
                Long mediumSales = entry.getValue();
                double ratio = (mediumSales * 100.0) / totalLargeCategorySales;
                mediumSalesRatio.put(mediumCategory, Math.round(ratio * 10.0) / 10.0); // 소수점 첫째 자리까지 반올림
            }

            categorySalesRatio.put(largeCategory, mediumSalesRatio);
        }

        return categorySalesRatio;
    }
}
