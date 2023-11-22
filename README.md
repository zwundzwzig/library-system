# 도서관리 시스템

## ERD
<img src="https://sokuri-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A9%E1%84%89%E1%85%A5%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%B5_%E1%84%89%E1%85%B5%E1%84%89%E1%85%B3%E1%84%90%E1%85%A6%E1%86%B7_erd.png" />

## 테이블 정의
전체 테이블 공통 컬럼
- created_date : row 생성 시점
- modified_date : row 변경 시점

### books
- title : 책 제목
- author : 책 저자
- status : 상태, enum[ 'available' (대출 가능), 'finish' (반납), 'on_loan' (대출 중), 'past_due' (연체) ]

### users
- nickname : 사용자 아이디
- email : 이메일 주소
- password : 비밀번호
- role : 권한

### loans
- checkout_date : 대출 일자
- return_date : 반납 일자
- status : 상태, enum[ 'available' (대출 가능), 'finish' (반납), 'on_loan' (대출 중), 'past_due' (연체) ]
- book_id, user_id : 외래키 

---

### AWS 구축도

서버 : EC2
- 플랫폼 : Amazon Linux

DB : EC2 
- 엔진 : MariaDB 10.6.14

버킷 : S3 

---
### 개발 환경
- JDK-17.0.5
- SpringBoot 3.1.4
- Gradle-8.0.2
- Junit-5.8.2
- MariaDB-10.6.14
