- 성별, 연령별 유동인구

GET http://localhost:8080/api/population/gender-age-ratio
```json
{
    "10대": {
        "MALE": 5.1,
        "FEMALE": 6.3
    },
    "70대 이상": {
        "MALE": 4.1,
        "FEMALE": 4.5
    },
    "30대": {
        "MALE": 24.8,
        "FEMALE": 21.7
    },
    "20대": {
        "MALE": 30.9,
        "FEMALE": 38.1
    },
    "50대": {
        "MALE": 12.3,
        "FEMALE": 10.2
    },
    "40대": {
        "MALE": 15.0,
        "FEMALE": 11.8
    },
    "60대": {
        "MALE": 7.7,
        "FEMALE": 7.2
    },
    "10대 미만": {
        "MALE": 0.1,
        "FEMALE": 0.2
    }
}
```

- 시간대별 유동인구

GET http://localhost:8080/api/population/time-zone-ratio
```json
{
    "11~14시": 17.3,
    "21~24시": 12.0,
    "06~11시": 12.3,
    "17~21시": 27.5,
    "14~17시": 19.6,
    "00~06시": 11.4
}
```