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
public class TimeSales {

    @Autowired
    private SalesRepository salesRepository;

    public Map<String, Double> calculateTimeSalesRatio(String place) {
        List<Sales> filteredSalesList = salesRepository.findByPlace(place).stream()
                .filter(sales -> {
                    Long yearMonth = sales.getYearMonth();
                    return yearMonth >= 202404 && yearMonth <= 202406;
                })
                .collect(Collectors.toList());
        Map<String, Long> timeSalesSum = new HashMap<>();
        long totalSales = 0;

        // 시간대별 매출 합계를 저장할 맵 초기화
        timeSalesSum.put("06~09시", 0L);
        timeSalesSum.put("09~12시", 0L);
        timeSalesSum.put("12~15시", 0L);
        timeSalesSum.put("15~18시", 0L);
        timeSalesSum.put("18~21시", 0L);
        timeSalesSum.put("21~24시", 0L);
        timeSalesSum.put("24~06시", 0L);

        // 각 시간대별 매출을 누적하여 합산
        for (Sales sales : filteredSalesList) {
            timeSalesSum.put("06~09시", timeSalesSum.get("06~09시") + Long.parseLong(sales.getTime0609().replace(",", "").trim()));
            timeSalesSum.put("09~12시", timeSalesSum.get("09~12시") + Long.parseLong(sales.getTime0912().replace(",", "").trim()));
            timeSalesSum.put("12~15시", timeSalesSum.get("12~15시") + Long.parseLong(sales.getTime1215().replace(",", "").trim()));
            timeSalesSum.put("15~18시", timeSalesSum.get("15~18시") + Long.parseLong(sales.getTime1518().replace(",", "").trim()));
            timeSalesSum.put("18~21시", timeSalesSum.get("18~21시") + Long.parseLong(sales.getTime1821().replace(",", "").trim()));
            timeSalesSum.put("21~24시", timeSalesSum.get("21~24시") + Long.parseLong(sales.getTime2124().replace(",", "").trim()));
            timeSalesSum.put("24~06시", timeSalesSum.get("24~06시") + Long.parseLong(sales.getTime2406().replace(",", "").trim()));
        }

        // 모든 시간대의 매출 합계를 계산하여 총 매출에 저장
        totalSales = timeSalesSum.values().stream().mapToLong(Long::longValue).sum();

        // 각 시간대의 매출 비율을 계산하고 소수점 첫째 자리까지 반올림하여 저장
        Map<String, Double> salesRatio = new HashMap<>();
        for (Map.Entry<String, Long> entry : timeSalesSum.entrySet()) {
            double ratio = (entry.getValue() * 100.0) / totalSales;
            salesRatio.put(entry.getKey(), Math.round(ratio * 10.0) / 10.0);  // 소수점 첫째 자리까지 반올림
        }

        return salesRatio;
    }
}

