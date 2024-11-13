package com.example.localens.analysis.service;

import com.example.localens.analysis.domain.Sales;
import com.example.localens.analysis.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DowSales {

    @Autowired
    private SalesRepository salesRepository;

    public Map<String, Double> calculateDailySalesRatio(String place) {
        List<Sales> salesList = salesRepository.findByPlace(place);
        Map<String, Long> dailySalesSum = new HashMap<>();
        long totalSales = 0;

        // 각 요일별 매출 합계를 저장할 맵 초기화
        dailySalesSum.put("월요일", 0L);
        dailySalesSum.put("화요일", 0L);
        dailySalesSum.put("수요일", 0L);
        dailySalesSum.put("목요일", 0L);
        dailySalesSum.put("금요일", 0L);
        dailySalesSum.put("토요일", 0L);
        dailySalesSum.put("일요일", 0L);

        // 각 요일별 매출을 누적하여 합산
        for (Sales sales : salesList) {
            dailySalesSum.put("월요일", dailySalesSum.get("월요일") + Long.parseLong(sales.getMon().replace(",", "").trim()));
            dailySalesSum.put("화요일", dailySalesSum.get("화요일") + Long.parseLong(sales.getTue().replace(",", "").trim()));
            dailySalesSum.put("수요일", dailySalesSum.get("수요일") + Long.parseLong(sales.getWed().replace(",", "").trim()));
            dailySalesSum.put("목요일", dailySalesSum.get("목요일") + Long.parseLong(sales.getThu().replace(",", "").trim()));
            dailySalesSum.put("금요일", dailySalesSum.get("금요일") + Long.parseLong(sales.getFri().replace(",", "").trim()));
            dailySalesSum.put("토요일", dailySalesSum.get("토요일") + Long.parseLong(sales.getSat().replace(",", "").trim()));
            dailySalesSum.put("일요일", dailySalesSum.get("일요일") + Long.parseLong(sales.getSun().replace(",", "").trim()));
        }

        // 모든 요일의 매출 합계를 계산하여 총 매출에 저장
        totalSales = dailySalesSum.values().stream().mapToLong(Long::longValue).sum();

        // 각 요일의 매출 비율을 계산하고 소수점 첫째 자리까지 반올림하여 저장
        Map<String, Double> salesRatio = new HashMap<>();
        for (Map.Entry<String, Long> entry : dailySalesSum.entrySet()) {
            double ratio = (entry.getValue() * 100.0) / totalSales;
            salesRatio.put(entry.getKey(), Math.round(ratio * 10.0) / 10.0);  // 소수점 첫째 자리까지 반올림
        }

        return salesRatio;
    }
}


