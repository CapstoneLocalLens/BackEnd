package com.example.localens.improvement.controller;

import com.example.localens.analysis.controller.DateController;
import com.example.localens.analysis.dto.RadarCongestionRateResponse;
import com.example.localens.analysis.dto.RadarFloatingPopulationResponse;
import com.example.localens.analysis.dto.RadarStayDurationChangeResponse;
import com.example.localens.analysis.dto.RadarStayPerVisitorResponse;
import com.example.localens.analysis.dto.RadarStayVisitRatioResponse;
import com.example.localens.analysis.dto.RadarVisitConcentrationResponse;
import com.example.localens.analysis.service.DateAnalysisService;
import com.example.localens.analysis.service.RadarComparisonService;
import com.example.localens.analysis.service.RadarCongestionRateService;
import com.example.localens.analysis.service.RadarFloatingPopulationService;
import com.example.localens.analysis.service.RadarInfoService;
import com.example.localens.analysis.service.RadarStayDurationChangeService;
import com.example.localens.analysis.service.RadarStayPerVisitorService;
import com.example.localens.analysis.service.RadarStayVisitRatioService;
import com.example.localens.analysis.service.RadarVisitConcentrationService;
import com.example.localens.customfeature.domain.CustomFeature;
import com.example.localens.improvement.domain.Event;
import com.example.localens.improvement.repository.EventMetricsRepository;
import com.example.localens.improvement.repository.EventRepository;
import com.example.localens.improvement.repository.MetricRepository;
import com.example.localens.improvement.service.ImprovementService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/improvements")
public class ImprovementController {

    private final ImprovementService improvementService;
    private final MetricRepository metricRepository;
    private final EventRepository eventRepository;
    private final EventMetricsRepository eventMetricsRepository;
    private final DateController dateController;
    private final DateAnalysisService dateAnalysisService;

    private final RadarComparisonService radarComparisonService;
    private final RadarFloatingPopulationService radarFloatingPopulationService;
    private final RadarStayVisitRatioService radarStayVisitRatioService;
    private final RadarCongestionRateService radarCongestionRateService;
    private final RadarStayPerVisitorService radarStayPerVisitorService;
    private final RadarStayDurationChangeService radarStayDurationChangeService;
    private final RadarInfoService radarInfoService;
    private final RadarVisitConcentrationService radarVisitConcentrationService;
    private MessageUtil log;

    @Autowired
    public ImprovementController(ImprovementService improvementService,
                                 MetricRepository metricRepository,
                                 EventRepository eventRepository,
                                 EventMetricsRepository eventMetricsRepository,
                                 DateController dateController,
                                 DateAnalysisService dateAnalysisService,
                                 RadarComparisonService radarComparisonService,
                                 RadarFloatingPopulationService radarFloatingPopulationService,
                                 RadarStayVisitRatioService radarStayVisitRatioService,
                                 RadarCongestionRateService radarCongestionRateService,
                                 RadarStayPerVisitorService radarStayPerVisitorService,
                                 RadarStayDurationChangeService radarStayDurationChangeService,
                                 RadarInfoService radarInfoService,
                                 RadarVisitConcentrationService radarVisitConcentrationService) {
        this.improvementService = improvementService;
        this.metricRepository = metricRepository;
        this.eventRepository = eventRepository;
        this.eventMetricsRepository = eventMetricsRepository;
        this.dateController = dateController;
        this.dateAnalysisService = dateAnalysisService;
        this.radarComparisonService = radarComparisonService;
        this.radarFloatingPopulationService = radarFloatingPopulationService;
        this.radarStayVisitRatioService = radarStayVisitRatioService;
        this.radarCongestionRateService = radarCongestionRateService;
        this.radarStayPerVisitorService = radarStayPerVisitorService;
        this.radarStayDurationChangeService = radarStayDurationChangeService;
        this.radarInfoService = radarInfoService;
        this.radarVisitConcentrationService = radarVisitConcentrationService;
    }

    @GetMapping("/recommendations/{districtUuid1}/{districtUuid2}")
    public ResponseEntity<Map<String, Object>> recommendEventsWithMetrics(
            @PathVariable Integer districtUuid1,
            @PathVariable Integer districtUuid2) {

        // 두 상권의 데이터를 각각 가져옴
        Map<String, Object> district1Data = radarComparisonService.constructDistrictData(
                districtUuid1,
                radarFloatingPopulationService,
                radarStayVisitRatioService,
                radarCongestionRateService,
                radarStayPerVisitorService,
                radarVisitConcentrationService,
                radarStayDurationChangeService,
                radarInfoService
        );

        Map<String, Object> district2Data = radarComparisonService.constructDistrictData(
                districtUuid2,
                radarFloatingPopulationService,
                radarStayVisitRatioService,
                radarCongestionRateService,
                radarStayPerVisitorService,
                radarVisitConcentrationService,
                radarStayDurationChangeService,
                radarInfoService
        );

        // 두 상권의 overallData 추출
        Map<String, Integer> district1Overall = (Map<String, Integer>) district1Data.get("overallData");
        Map<String, Integer> district2Overall = (Map<String, Integer>) district2Data.get("overallData");

        // 각 지표의 차이를 계산하여 저장할 리스트 생성
        List<Map.Entry<String, Integer>> differences = new ArrayList<>();

        for (String key : district1Overall.keySet()) {
            if (district2Overall.containsKey(key)) {
                int value1 = district1Overall.get(key);
                int value2 = district2Overall.get(key);
                int difference = Math.abs(value1 - value2);
                differences.add(new AbstractMap.SimpleEntry<>(key, difference));
            }
        }

        // 차이를 기준으로 내림차순 정렬
        differences.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // 차이가 가장 많이 나는 지표 두 개 추출
        List<String> topTwoDifferences = new ArrayList<>();
        if (differences.size() >= 2) {
            topTwoDifferences.add(differences.get(0).getKey());
            topTwoDifferences.add(differences.get(1).getKey());
        } else if (differences.size() == 1) {
            topTwoDifferences.add(differences.get(0).getKey());
        }

        List<UUID> metricsUuids = new ArrayList<>();
        for (String metricName : topTwoDifferences) {
            // event_metric_change_type 테이블에서 metrics_uuid 찾기
            UUID metricsUuid = metricRepository.findMetricsUuidByMetricsName(metricName);
            if (metricsUuid != null) {
                metricsUuids.add(metricsUuid);
            }
        }

        // metrics_uuid가 event_metrics 테이블에서 매칭되는 event_uuid 찾기
        List<UUID> eventUuids = eventMetricsRepository.findEventUuidByMetricsUuidIn(metricsUuids);

        // 찾은 event_uuid를 통해 event 테이블에서 이벤트 정보 가져오기
        List<Event> events = eventRepository.findAllById(eventUuids);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        List<Map<String, Object>> improveMethodList = new ArrayList<>();
        List<Map<String, Object>> beforeOverallDataList = new ArrayList<>();
        List<Map<String, Object>> afterOverallDataList = new ArrayList<>();
        List<String> beforeDates = new ArrayList<>();
        List<String> afterDates = new ArrayList<>();
        List<String> changedFeatureNames = new ArrayList<>();
        List<Integer> changedFeatureValues = new ArrayList<>();

        for (Event event : events) {
            if (event != null) {
                Map<String, Object> improveMethod = new HashMap<>();
                improveMethod.put("image", event.getEventImg());
                improveMethod.put("name", event.getEventName());
                improveMethod.put("date",
                        event.getEventStart().format(formatter) + " ~ " + event.getEventEnd().format(formatter));
                improveMethod.put("area", event.getEventPlace());
                improveMethod.put("detail", event.getInfo());
                improveMethod.put("uuid", event.getEventUuid().toString());
                improveMethodList.add(improveMethod);

                Map<String, Object> date1Result = dateAnalysisService.calculateDateData(event.getEventPlaceInt(),
                        event.getEventStart().toString());
                Map<String, Object> date2Result = dateAnalysisService.calculateDateData(event.getEventPlaceInt(),
                        event.getEventEnd().toString());

                //미완성
                Map<String, Integer> date1Values = (Map<String, Integer>) date1Result.get("values");
                Map<String, Integer> date2Values = (Map<String, Integer>) date2Result.get("values");

                // 두 날짜 간의 차이를 계산
                Map<String, Integer> diffMap = new LinkedHashMap<>();
                for (String key : date1Values.keySet()) {
                    if (date2Values.containsKey(key)) {
                        int v1 = date1Values.get(key);
                        int v2 = date2Values.get(key);
                        int diff = Math.abs(v2 - v1);
                        diffMap.put(key, diff);
                    }
                }

                // 차이가 가장 큰 지표 찾기
                Map.Entry<String, Integer> maxEntry = diffMap.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElse(null);

                if (maxEntry != null) {
                    // 가장 큰 차이를 갖는 지표명, 값 추가
                    changedFeatureNames.add(maxEntry.getKey());
                    changedFeatureValues.add(maxEntry.getValue());
                }

                // 여기서 beforeOverallDataList, afterOverallDataList, beforeDates, afterDates에
                // 필요한 값들을 추가하는 로직도 구현할 수 있음.
                // 예: beforeOverallDataList.add(date1Values) 등

                beforeOverallDataList.add(Map.of("values", date1Values));
                afterOverallDataList.add(Map.of("values", date2Values));
                beforeDates.add(event.getEventStart().format(formatter));
                afterDates.add(event.getEventEnd().format(formatter));
            }
        }

        Map<String, Object> before = new LinkedHashMap<>();
        before.put("overallData", beforeOverallDataList);
        before.put("date", beforeDates);

        Map<String, Object> after = new LinkedHashMap<>();
        after.put("overallData", afterOverallDataList);
        after.put("date", afterDates);

        Map<String, Object> beforeAndAfter = new LinkedHashMap<>();
        beforeAndAfter.put("before", before);
        beforeAndAfter.put("after", after);
        beforeAndAfter.put("changedFeature", Map.of(
                "name", changedFeatureNames,
                "value", changedFeatureValues
        ));

        // 최종 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("ImproveMethod", improveMethodList);
        response.put("beforeAndAfter", beforeAndAfter);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
