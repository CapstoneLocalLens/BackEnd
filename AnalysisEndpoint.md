- 성별, 연령별 유동인구

GET http://localhost:8080/api/population/gender-age-ratio/{place}

ex) http://localhost:8080/api/population/gender-age-ratio/강남
```json
{
  "70대 이상": {
    "MALE": 3.7,
    "FEMALE": 3.2
  },
  "10대": {
    "MALE": 5.8,
    "FEMALE": 7.1
  },
  "30대": {
    "MALE": 26.0,
    "FEMALE": 24.5
  },
  "20대": {
    "MALE": 29.1,
    "FEMALE": 37.7
  },
  "50대": {
    "MALE": 11.8,
    "FEMALE": 9.1
  },
  "40대": {
    "MALE": 16.5,
    "FEMALE": 12.3
  },
  "60대": {
    "MALE": 6.9,
    "FEMALE": 6.0
  },
  "10대 미만": {
    "MALE": 0.1,
    "FEMALE": 0.1
  }
}
```

- 시간대별 유동인구

GET http://localhost:8080/api/population/time-zone-ratio/{place}

ex) http://localhost:8080/api/population/time-zone-ratio/강남
```json
{
  "11~14시": 18.1,
  "21~24시": 12.5,
  "06~11시": 11.9,
  "17~21시": 29.0,
  "14~17시": 19.9,
  "00~06시": 8.7
}
```

- 요일별 유동인구

GET http://localhost:8080/api/population/weekday-ratio/{place}

ex) http://localhost:8080/api/population/weekday-ratio/강남
```json
{
  "화요일": 14.4,
  "일요일": 11.2,
  "수요일": 14.0,
  "토요일": 16.5,
  "목요일": 14.1,
  "금요일": 16.3,
  "월요일": 13.4
}
```

- 요일별 매출

GET http://localhost:8080/api/sales/weekday-ratio/{place}

ex) http://localhost:8080/api/sales/weekday-ratio/강남상권
```json
{
    "화요일": 15.3,
    "수요일": 14.4,
    "일요일": 6.7,
    "토요일": 15.8,
    "목요일": 14.9,
    "금요일": 17.8,
    "월요일": 15.2
}
```

- 시간대별 매출

GET http://localhost:8080/api/sales/time-ratio/{place}

ex) http://localhost:8080/api/sales/time-ratio/강남상권
```json
{
    "09~12시": 19.0,
    "12~15시": 28.4,
    "24~06시": 2.6,
    "21~24시": 7.6,
    "06~09시": 1.6,
    "15~18시": 22.3,
    "18~21시": 18.4
}
```

- 성별, 연령대별 매출

GET http://localhost:8080/api/sales/gender-age-ratio/{place}

ex) http://localhost:8080/api/sales/gender-age-ratio/강남상권
```json
{
    "30대": {
        "여성": 31.2,
        "남성": 30.5
    },
    "20대": {
        "여성": 12.2,
        "남성": 10.8
    },
    "50대": {
        "여성": 23.7,
        "남성": 24.0
    },
    "60대이상": {
        "여성": 9.5,
        "남성": 10.0
    },
    "40대": {
        "여성": 23.4,
        "남성": 24.6
    }
}
```