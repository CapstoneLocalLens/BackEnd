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

- 대분류별 중분류 매출

GET http://localhost:8080/api/sales/large-medium-category-ratio/{place}

ex) http://localhost:8080/api/sales/large-medium-category-ratio/강남상권
```json
{
    "학문/교육": {
        "기타교육": 31.4,
        "독서실/고시원": 2.0,
        "입시학원": 31.2,
        "기술/직업교육학원": 16.9,
        "외국어학원": 13.4,
        "유아교육": 1.8,
        "예체능계학원": 3.4
    },
    "생활서비스": {
        "차량관리/서비스": 8.8,
        "광고/인쇄/인화": 7.2,
        "세탁/가사서비스": 1.1,
        "가례서비스": 0.3,
        "미용서비스": 68.6,
        "수리서비스": 14.0
    },
    "의료/건강": {
        "수의업": 0.3,
        "의약/의료품": 7.2,
        "일반병원": 35.9,
        "특화병원": 56.7
    },
    "여가/오락": {
        "일반스포츠": 35.6,
        "요가/단전/마사지": 9.0,
        "취미/오락": 55.3
    },
    "음식": {
        "닭/오리요리": 1.6,
        "커피/음료": 13.3,
        "일식/수산물": 7.7,
        "분식": 4.3,
        "간이주점": 13.8,
        "제과/제빵/떡/케익": 1.7,
        "고기요리": 13.5,
        "양식": 7.7,
        "중식": 5.9,
        "한식": 21.3,
        "별식/퓨전요리": 1.2,
        "패스트푸드": 3.0,
        "유흥주점": 5.0
    },
    "소매/유통": {
        "음/식료품소매": 3.2,
        "화장품소매": 8.2,
        "인테리어/가정용품": 35.2,
        "건강/기호식품": 1.4,
        "서적/도서": 6.5,
        "가전제품": 1.0,
        "패션잡화": 4.1,
        "선물/완구": 2.0,
        "종합소매점": 24.1,
        "사무/교육용품": 3.8,
        "의복/의류": 10.4
    }
}
```