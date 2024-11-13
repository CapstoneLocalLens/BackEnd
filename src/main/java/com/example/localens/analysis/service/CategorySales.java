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
public class CategorySales {

    @Autowired
    private SalesRepository salesRepository;

    public Map<String, Double> calculateLargeCategorySalesRatioByPlace(String place) {
        List<Sales> filteredSalesList = salesRepository.findByPlace(place).stream()
                .filter(sales -> {
                    Long yearMonth = sales.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406;
                })
                .collect(Collectors.toList());

        Map<String, Long> largeCategorySalesSum = new HashMap<>(); // 대분류업종별 총 매출
        long totalSales = 0;

        // 대분류업종별 매출을 누적하여 합산
        for (Sales sales : filteredSalesList) {
            String largeCategory = sales.getLargeCategory();
            Long salesAmount = Long.parseLong(sales.getSalesAmount().replace(",", "").trim());

            largeCategorySalesSum.put(largeCategory, largeCategorySalesSum.getOrDefault(largeCategory, 0L) + salesAmount);
            totalSales += salesAmount;
        }

        // 대분류업종별 매출 비율 계산
        Map<String, Double> largeCategorySalesRatio = new HashMap<>();

        for (Map.Entry<String, Long> entry : largeCategorySalesSum.entrySet()) {
            String largeCategory = entry.getKey();
            Long sales = entry.getValue();
            double ratio = (sales * 100.0) / totalSales;
            largeCategorySalesRatio.put(largeCategory, Math.round(ratio * 10.0) / 10.0); // 소수점 첫째 자리까지 반올림
        }

        return largeCategorySalesRatio;
    }
}
