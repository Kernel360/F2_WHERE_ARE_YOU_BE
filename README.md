# FINAL PROJECT - 배드민턴 칠까?

커널360 BE 2기 FINAL PROJECT 3팀, "콕콕"입니다 ❕

<br>

## 프로젝트 소개

### 🧚 저희 서비스는 이런 서비스입니다!

- 콕콕에서 **다양한 사람들**과, **특색 있는 동호회**에서, 같이 배드민턴 쳐요! 🏸

- 토너먼트, 프리, 단식, 복식에 따라 **대진표**를 만들어보세요! 🤼

- 다른 사람들의 경기가 궁금하다면, **실시간 경기 현황**을 확인해보세요! 📌 


### 💁 누가 사용하면 좋을까요?

- 다양한 사람들과 배드민턴을 즐겁게 쳐보고 싶으신 분! 🧑‍🧑‍🧒‍🧒

- 지난 경기의 내 **승/패 여부**와 **점수**를 보고 싶으신 분! 🏅

- 대진표를 수기로 만들거나 제비뽑기로 정하기 귀찮으신 분! 🗳️

- 동호회를 운영 중인데 경기 일정 관리가 어렵거나, 동호회원들을 더 편하게 관리하고 싶으신 분! 📆

- 모두 콕콕에서 모여요! 🏸

<br>


## 👧 팀원 구성

<table>

  <tr>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Frontend</b></td>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Backend</b></td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/soeunnPark">
        <img src="https://github.com/user-attachments/assets/ac25ece8-4c7e-4651-9c53-4f5c82d2487f" width="100px;" alt="박소은"/><br />
        <sub><b>팀장 : 박소은</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Yejin0O0">
        <img src="https://github.com/user-attachments/assets/d7c93921-c0bd-4a9b-8891-5c7bfabb0cc9" width="100px;" alt="윤예진"/><br />
        <sub><b>팀원 : 윤예진</b></sub>
      </a>
    </td>	  
    <td align="center">
      <a href="https://github.com/km2535">
        <img src="https://github.com/user-attachments/assets/ab2803a4-e389-4861-887f-c3b8db822b62" width="100px;" alt="이강민"/><br />
        <sub><b>팀원 : 이강민</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/I-migi">
        <img src="https://github.com/user-attachments/assets/7a5e4048-829e-452d-80c9-1eb0df3bb848" width="100px;" alt="이선우"/><br />
        <sub><b>팀원 : 이선우</b></sub>
      </a>
    </td>
  </tr>
</table>

<br>

## 🍳 기술 스택


### 1. Back-end

    - JAVA 17
    - Spring Boot 3.3.2
    - Spring Data JPA

### 2. Front-end

    - Next.js

### 3. Database

    - MySQL 8
    - Docker


<br>


### 브랜치 전략

- Git-flow 전략을 기반으로 main, develop, release, test 브랜치와 feature 보조 브랜치를 운용했습니다. 
- main, develop, release, test, feature 브랜치로 나누어 개발을 했습니다.

  - develop 브랜치는 개발 단계에서 git-flow의 master 역할을 하는 브랜치입니다.
  - release 브랜치는 main에 push하기 전에 배포테스트를 하기 위한 브랜치입니다.
  - test 브랜치는 프론트엔드 작업자 분들과 함께 API 테스트를 하기 위한 브랜치입니다.
  - feature 브랜치는 기능 단위로 독립적인 개발 환경을 위하여 사용하고 merge 후 각 브랜치를 삭제해주었습니다. 


<br>


## 📗 프로젝트 구조


```
F2_PLAY_BADMINTON_BE/
│
├── .github/
├── .gradle/
├── .idea/
├── badminton-api/
│   ├── bin/
│   ├── build/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── org.badminton.api/
│   │   │   └── resources/
│   │   │       ├── .env
│   │   │       ├── application.yaml
│   │   │       ├── data.sql
│   │   │       └── logback-spring.xml
│   │   └── test/
│   ├── build.gradle
│   └── Dockerfile
├── badminton-batch/
├── badminton-domain/
├── bin/
├── gradle/
├── logs/
├── .env
├── .gitignore
├── build.gradle
├── docker-compose.yaml
├── gradlew
└── settings.gradle


```


<br>

<br>


## ⏰ 개발 기간 및 작업 관리


### 개발 기간

- 전체 개발 기간 : 2024-09-09 ~ 2024-10-17

### 작업 관리

- GitHub Projects와 Issues를 사용하여 진행 상황을 공유했습니다.
- Swagger로 개발한 API를 문서화해서 프론트엔드분들과 함께 공유했습니다.
- 개발 중 팀원들과 논의할 사항들을 GitHub Discussions에 등록했습니다.
- Github Wiki를 사용하여 개발 중 공부한 내용을 문서로 작성했습니다.
- 10분에서 15분의 스크럼과 회고를 통해 개발 방향성에 대한 고민을 나누고 Notion에 회의 내용을 기록했습니다.


<br>



## 🧚 기능 구현


### 1. 기본 정보 상세 조회

- 이미지, 동호회 이름, 소개글
- 동호회 티어

**`가입하지 않고 사용할 수 있는 기능`**

### 2. 동호회 가입하기

- 가입하지 않은 사용자에 대해 가입 버튼 활성화

**`가입해야 사용할 수 있는 기능`  `동호회장`** **`운영진`**

### 3. 경기 생성, 수정, 삭제

- 경기 제목
- 소개글(게시글과 유사)
- 시간, 장소
- 티어 제한
    - 누구나 참여할 수 있는 경기 → 랜덤으로 !!
    - 상/중/하 → `최소 티어`
- 모집 기한
- 모집 인원
- **`단식`**인지 **`복식`**인지
- 경기 상태
    - 모집 중
    - 모집 완료
    - 완료

**`가입해야 사용할 수 있는 기능` `모든 가입자`**

### 4. 경기 조회

- 달력 경기 프리뷰 → 한 달 동안의 경기(경기 제목, 경기 모집 상태, 경기 날짜)
- 달력 날짜를 누르면 경기 일정 리스트 조회 →
- 시간, 장소, 티어 제한, 소개글, 모집 기한, 모집 인원
- 경기 상태
    - 모집 인원 미달 시 경기 취소
    - 모집 인원 도달 시 경기 마감

**`가입해야 사용할 수 있는 기능` `모든 가입자`**

### 5. 경기 참여 신청하기, 취소하기

- 티어 미달 시 참여 신청 불가
- 경기 일정 용어 → 명확하게 수정(event)

**`가입해야 사용할 수 있는 기능` `모든 가입자`** 

### 6. 대진표 조회, 생성, 다시 생성하기

- 만들어진 대진표를 조회
- 모집 기한이 지나자마자 최소 인원이 모이면 대진표가 자동으로 만들어진다.
- 최소 인원이 모이지 않았다면 경기가 취소 상태로 변경됨

<추가 기능>

- 당일 인원(외부인) 추가 및 대진표 수정
- 인원 설정 시 제한을 둬야 할 것 같다. (**`fix`** 하는 것이 좋을 것 같다)
    - **`단식`**일 경우 → 짝수만
    - **`복식`**일 경우 → **`4`**의 배수만

<확장성 - 대진표 생성 rule>

- **`admin`**에서 **`rule`**을 추가
    - event를 등록하는 총무 → matching 방법 (라디오 버튼)
    - 지금은 **`random`** 만 enable
    - 나이순, 성별, 티어(구력이 비슷한 사람끼리)
    - 해당 rule에 대해 matching이 될 수 있도록 !!
- rule strategy (**`Interface`**)
    - 지금은 random으로만❗️
    - 정책 패턴

**`가입해야 사용할 수 있는 기능` `모든 가입자`** 

### 7. 경기 결과 확인

- 경기 스코어 기록 → 저장 → 어디서 볼 수 있을까?

<추가 기능>

- 경기 결과 스크린샷을 공유할 수 있도록
- 경기 결과를 SNS에 올리도록 할 수 있도록!!

**`가입해야 사용할 수 있는 기능` `동호회장`** **`운영진`**

### 8. 동호회 설정

- 회원 Role 관리 → 동호회장, 운영진
- 권한 설정
- 동호회 탈퇴
- 동호회 기능 사용 중지(며칠?)

**`가입하지 않고 사용할 수 있는 기능`**

### 9. 동호회 멤버 조회

- 티어별로 조회
- 마우스 오버했을 때 회원 간단 정보를 조회 가능

<추가사항>

- 랭킹 조회

## 4️⃣ 마이페이지

### 1. 개인 정보 조회

이름

티어

프로필 이미지

전적(?승?패?무)

### 2. 가입한 동호회 정보

### 3. 지난 경기 일정을 조회

## 5️⃣ 그룹 점수, 개인 점수  (티어 Rating)

### 1. 종합 점수제

- 티어를 기록하는 기준: 승패율 + 경기 횟수 + 경력
- → Rating

### 2. 그룹 점수와 개인 점수

- 경기 결과는 개인 점수에만 반영, 개인 점수는 티어로 표현 가능
- 그룹 점수는 그룹원들의 티어 분포도 !!!
- 실력 점수, 활동 점수
- 승률
- 그룹 내에서만 쓸 수 있는, 외부에 보여줄 수 있는 것을 구분

  
<br>


## 🌟 구현 현황


<br>


### 🫥 1. 서비스와 동호회의 가입여부와 역할에 따라 사용할 수 있는 기능 구분


#### [서비스에 로그인하지 않아도 사용할 수 있는 기능]

- 전체 동호회 조회
- 동호회 소개 페이지 조회


<br>

#### [서비스에 로그인하고 동호회에 가입하지 않아도 사용할 수 있는 기능]

- 프로필 사진 수정
- 로그아웃
- 이름, 이메일, 프로필 사진 조회
- 회원 탈퇴
- 동호회 가입 신청

<br>


#### [동호회에 가입하고 역할이 ROLE_USER일 때 사용할 수 있는 기능]

- 가입한 동호회 이름, 역할, 티어, 전적 조회
- 경기 참여 신청 및 취소
- 특정 경기 월별, 일별로 조회
- 대진표 조회
- 게임의 세트 상세 점수 조회

<br>

#### [동호회에 가입하고 역할이 ROLE_MANAGER일 때 사용할 수 있는 기능]

- 동호회 수정
- 경기 생성, 삭제 및 수정
- 대진표 생성
- 세트별 점수 저장
- 동호회원 강제 탈퇴
- 동호회원 정지
  
<br>

#### [동호회에 가입하고 역할이 ROLE_OWNER일 때 사용할 수 있는 기능]

- 동호회원 역할 변경

<br>


### 🗾 2. 스프링 시큐리티, oAuth 로그인

- oAuth 로그인은 `naver`, `kakao`, `google` API 사용

```java
@Bean
	@Order(2)
	public SecurityFilterChain clubFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/v1/clubs/**")
			.csrf(AbstractHttpConfigurer::disable)
			.cors(this::corsConfigurer)
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, clubMemberService),
				UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(new ClubMembershipFilter(clubMemberService), JwtAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/v1/clubs", "/v1/clubs/{clubId}", "/v1/clubs/search")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/v1/clubs")
				.permitAll()
				.requestMatchers(HttpMethod.DELETE, "/v1/clubs/{clubId}")
				.access(hasClubRole("OWNER"))
				.requestMatchers(HttpMethod.PATCH, "/v1/clubs/{clubId}")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubId}/leagues/{leagueId}")
				.access(hasClubRole("OWNER", "MANAGER", "USER"))
				.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubId}/clubMembers")
```

필터를 사용해서 권한 별 api 사용 구현

### 🔴 3. Custom Exception

```java
@Getter
public class BadmintonException extends RuntimeException {

	private final ErrorCode errorCode;
	private final String errorMessage;

	public BadmintonException(ErrorCode errorCode, String errorDetails) {
		this(errorCode, errorDetails, null);
	}

	public BadmintonException(ErrorCode errorCode, String errorDetails, Exception e) {
		super(errorCode.getDescription() + errorDetails, e);
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription() + errorDetails;
	}

	public BadmintonException(ErrorCode errorCode) {
		this(errorCode, (Exception)null);
	}

	public BadmintonException(ErrorCode errorCode, Exception e) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
```

`RuntimeException` 을 상속받은 `BadmintonException` 을 만들어 커스텀 예외 처리 구현


<br>

### 📆 3. 배치

```java
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	public Job deleteMemberJob(Step deleteMemberStep, JobRepository jobRepository) {
		return new JobBuilder("deleteMemberJob", jobRepository)
			.start(deleteMemberStep)
			.build();
	}

	@Bean
	public Step deleteMemberStep(ItemReader<MemberEntity> reader, ItemProcessor<MemberEntity, MemberEntity> processor,
		ItemWriter<MemberEntity> writer, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("deleteMemberStep", jobRepository)
			.<MemberEntity, MemberEntity>chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
```
배치와 스케줄러를 사용해 주기적으로 회원 삭제 및 정지 해제 로직 실행

<br>

### 🐑 4. 배포

<img width="1121" alt="image" src="https://github.com/user-attachments/assets/5a59a5b6-7afd-4e3a-8add-cd8b98a6ba47">

`AWS`의 EC2와 RDS를 사용해서 배포
테스트용 EC2와 Production용 EC2 두 개 생성

```java
networks:

  backend:
    driver: bridge

services:
  badminton-api:
    build: ./badminton-api  # badminton-api 모듈에 있는 Dockerfile을 빌드
    ports:
      - "8080:8080"  # API 서비스의 포트
    networks:
      - backend
    image: speech2/badminton:api

  badminton-batch:
    build: ./badminton-batch  # badminton-batch 모듈에 있는 Dockerfile을 빌드
    ports:
      - "9090:9090"
    networks:
      - backend
    image: speech2/badminton:batch
```
Docker-compose.yaml 파일 사용해서 jar 파일 대체


### 👨‍💻 5. 로그

<img width="1090" alt="image" src="https://github.com/user-attachments/assets/610b98df-9cc6-4068-b89f-75e1d194f665">

`AWS`의 CloudWatch를 사용해서 로그 관리

<br>


### 🖼️ 6. 이미지 업로드

<img width="1055" alt="image" src="https://github.com/user-attachments/assets/6aba940a-824f-4b25-8533-a15d117aa33c">

`AWS`의 S3을 사용해서 회원 프로필 이미지나 동호회 배너 이미지 업로드

### 📊 7. 부하 테스트, 성능 테스트

 ![image](https://github.com/user-attachments/assets/d1aad490-fb28-43a9-a3ba-693b0d4ebdbf)

`jmeter` 를 사용해서 부하 테스트 및 성능 테스트

### 🔐 8. 키 보안

```java
spring:
  profiles:
    active: ${ACTIVE_PROFILE}
  application:
    name: badminton
  jpa:
    hibernate:
      ddl-auto: ${DDL_METHOD_API}
      show-sql: true
      properties:
        hibernate:
          format_sql: true
          dialect: org.hibernate.dialect.MySQL8Dialect
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    defer-datasource-initialization: true
  messages:
    encoding: UTF-8
    basename: messages
  sql:
    init:
      mode: ${SQL_MODE}
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USER_NAME}
    password: ${DATABASE_USER_PASSWORD}
    driver-class-name: ${DATABASE_DRIVER}
  jwt:
    secret: ${JWT_SECRET}
```

.Env 파일을 이용해서 키 공개 X

### 🔑 9. 검증

```java
@Constraint(validatedBy = ClubDescriptionValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClubDescriptionValidator {

	String message() default "동호회 소개란은 2자 이상 입력해야 하며 최대 1000자까지 입력할 수 있습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
```
커스텀 Validation을 사용해서 검증



## 🍏 추후 구현 사항

### Redis 사용

### Polling 사용

### 공공 API 사용


## BackEnd 깃 허브 주소
[GitHub GitHub - Kernel360/F2_WHERE_ARE_YOU_BE: kernel360 Final 프로젝트…](https://github.com/Kernel360/F2_PLAY_BADMINTON_BE)

## FrontEnd 깃 허브 주소
[GitHub GitHub - Kernel360/F2_WHERE_ARE_YOU_BE: kernel360 Final 프로젝트…](https://github.com/Kernel360/F2_PLAY_BADMINTON_BE)

## 피그마
https://www.figma.com/design/mx70EdVAm7gOnxGUIwztdJ/PLAY_BADMINTON?node-id=0-1&node-type=canvas

## Swagger
https://api.badminton.run/swagger-ui/index.html#/

## ERD
https://www.erdcloud.com/d/Z5BhhKZEMNaZAGa8R
