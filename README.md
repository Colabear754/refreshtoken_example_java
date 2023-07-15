# Refresh Token 구현 예제
### 이 프로젝트는 아래 내용을 구현한 예제입니다.
- 기존 [스프링 시큐리티와 JWT를 사용한 사용자 인증 예제 프로젝트](https://github.com/Colabear754/authentication_example_java)를 기반으로 Refresh Token을 적용한 예제
- 블로그의 [[JWT] Access Token의 한계와 Refresh Token의 필요성](https://colabear754.tistory.com/179)에 작성한 내용을 Java로 구현한 프로젝트
- Access Token의 유효시간을 줄이고 Refresh Token을 발급하여 Access Token을 재발급하는 방식으로 사용자 인증을 오래 유지

### 브랜치 정보
- master : Refresh Token을 적용한 브랜치
- base : Access Token만 적용한 기존 프로젝트

### 환경 정보
- Java 19
- Spring Boot 3.0.6
- Spring Security
- Springdoc 2.1.0
- Spring Data JPA
- JJWT 0.11.5
- H2 DB
- Gradle 7.6.1
- Lombok