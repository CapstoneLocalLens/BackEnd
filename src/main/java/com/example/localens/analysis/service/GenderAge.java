package com.example.localens.analysis.service;

import com.example.localens.analysis.domain.Hourly;
import com.example.localens.analysis.repository.PopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenderAge {

    @Autowired
    private PopulationRepository populationRepository;

    public Map<String, Map<String, Double>> getGenderAgeRatios() {
        // 1. 데이터 필터링: 2024년 4월 ~ 6월 (2024년 2분기) 데이터만 사용
        List<Hourly> filteredData = populationRepository.findAll().stream()
                .filter(data -> data.getYearMonth() >= 202404 && data.getYearMonth() <= 202406)
                .collect(Collectors.toList());

        // 2. 성별 총 인구 수 계산
        Map<String, Double> genderTotalPopulation = filteredData.stream()
                .collect(Collectors.groupingBy(
                        Hourly::getGender,
                        Collectors.summingDouble(Hourly::getVisitPopulation)
                ));

        // 3. 연령대 및 성별 인구 합계 계산
        Map<String, Map<String, Double>> ageGenderPopulation = filteredData.stream()
                .collect(Collectors.groupingBy(
                        Hourly::getAgeGroup,
                        Collectors.groupingBy(
                                Hourly::getGender,
                                Collectors.summingDouble(Hourly::getVisitPopulation)
                        )
                ));

        // 4. 성별 총 인구 수를 사용해 비율 계산
        Map<String, Map<String, Double>> ageGenderRatio = ageGenderPopulation.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // 연령대 (AgeGroup) 키 유지
                        ageEntry -> ageEntry.getValue().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey, // 성별 (Gender) 키 유지
                                        genderEntry -> {
                                            String gender = genderEntry.getKey();
                                            Double population = genderEntry.getValue();
                                            Double totalPopulation = genderTotalPopulation.getOrDefault(gender, 1.0);
                                            return Math.round((population / totalPopulation) * 1000) / 10.0;
                                        }
                                ))
                ));

        return ageGenderRatio;
    }
}
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class GenderAge {
//    private static final Logger logger = LoggerFactory.getLogger(GenderAge.class);
//
//    @Autowired
//    private PopulationRepository populationRepository;
//
//    public Map<String, Map<String, Double>> getGenderAgeRatios() {
//        List<Hourly> allData = populationRepository.findAll();
//        logger.info("All Data: {}", allData);
//
//
//        // 1. 데이터 필터링: 2024년 4월 ~ 6월 (2024년 2분기) 데이터만 사용
//        List<Hourly> filteredData = populationRepository.findAll().stream()
//                .filter(data -> data.getYearMonth() >= 202404L && data.getYearMonth() <= 202406L)
//                .collect(Collectors.toList());
//
//        logger.info("Filtered Data Size: {}", filteredData.size());
//        logger.info("Filtered Data: {}", filteredData);
//
//        if (filteredData.isEmpty()) {
//            logger.warn("No data found for the specified date range.");
//        }
//
//        // 2. 성별 총 인구 수 계산
//        Map<String, Double> genderTotalPopulation = filteredData.stream()
//                .collect(Collectors.groupingBy(
//                        Hourly::getGender,
//                        Collectors.summingDouble(Hourly::getVisitPopulation)
//                ));
//
//        logger.info("Gender Total Population: {}", genderTotalPopulation);
//
//        // 3. 연령대 및 성별 인구 합계 계산
//        Map<String, Map<String, Double>> ageGenderPopulation = filteredData.stream()
//                .collect(Collectors.groupingBy(
//                        Hourly::getAgeGroup,
//                        Collectors.groupingBy(
//                                Hourly::getGender,
//                                Collectors.summingDouble(Hourly::getVisitPopulation)
//                        )
//                ));
//
//        logger.info("Age Gender Population: {}", ageGenderPopulation);
//
//        // 4. 성별 총 인구 수를 사용해 비율 계산
//        Map<String, Map<String, Double>> ageGenderRatio = ageGenderPopulation.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey, // 연령대 (AgeGroup) 키 유지
//                        ageEntry -> ageEntry.getValue().entrySet().stream()
//                                .collect(Collectors.toMap(
//                                        Map.Entry::getKey, // 성별 (Gender) 키 유지
//                                        genderEntry -> {
//                                            String gender = genderEntry.getKey();
//                                            Double population = genderEntry.getValue();
//                                            Double totalPopulation = genderTotalPopulation.getOrDefault(gender, 1.0);
//                                            return (population / totalPopulation) * 100;
//                                        }
//                                ))
//                ));
//
//        logger.info("Age Gender Ratio: {}", ageGenderRatio);
//
//        return ageGenderRatio;
//    }
//}
