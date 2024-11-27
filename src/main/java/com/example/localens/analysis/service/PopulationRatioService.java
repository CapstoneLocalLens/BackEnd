package com.example.localens.analysis.service;

import com.example.localens.analysis.dto.PopulationRatioResponse;
import com.example.localens.analysis.repository.CommercialDistrictRepository;
import com.example.localens.influx.InfluxDBClientWrapper;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopulationRatioService {

    private final CommercialDistrictRepository districtRepository;
    private final InfluxDBClientWrapper influxDBClientWrapper;

    public PopulationRatioResponse getPopulationRatioByDistrictUuid(Integer districtUuid) {
        // MySQL에서 상권 이름 조회
        String districtName = districtRepository.findDistrictNameByDistrictUuid(districtUuid);
        if (districtName == null) {
            throw new IllegalArgumentException("Invalid districtUuid: " + districtUuid);
        }

        // InfluxDB 쿼리 작성
        String fluxQuery = String.format(
                "from(bucket: \"result_bucket\") " +
                        "|> range(start: 2024-01-01T00:00:00Z, stop: now()) " +
                        "|> filter(fn: (r) => r._measurement == \"total_population_ratio\" and r[\"place\"] == \"%s\" and exists r.tmzn and exists r._value) " +
                        "|> keep(columns: [\"tmzn\", \"_value\"])", districtName
        );

        // InfluxDB에서 데이터 조회
        List<FluxTable> queryResult = influxDBClientWrapper.query(fluxQuery);

        // 데이터 매핑
        Map<String, Double> timeZoneRatios = new LinkedHashMap<>();
        for (FluxTable table : queryResult) {
            for (FluxRecord record : table.getRecords()) {
                Object timeZoneObj = record.getValueByKey("tmzn");
                Object valueObj = record.getValueByKey("_value");

                // Null 값 처리
                if (timeZoneObj == null || valueObj == null) {
                    System.out.println("Null value found for record: " + record);
                    continue;
                }

                // 시간대와 비율 데이터 추가
                String timeZone = timeZoneObj.toString() + "시";
                double ratio = Math.round(Double.parseDouble(valueObj.toString()) * 10.0) / 10.0; // 소수점 첫째 자리 반올림
                timeZoneRatios.put(timeZone, ratio);
            }
        }

        // 시간대 정렬
        Map<String, Double> sortedTimeZoneRatios = timeZoneRatios.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> Integer.parseInt(entry.getKey().replace("시", "")))) // 숫자 기준 정렬
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, // 충돌 처리: 이전 값 유지
                        LinkedHashMap::new // 정렬된 순서 유지
                ));

        return new PopulationRatioResponse(sortedTimeZoneRatios);
    }
}
