
# badminton-api Dockerfile
# Ubuntu 20.04 기반 이미지 사용
FROM ubuntu:20.04

# 필수 패키지 업데이트 및 Java 17 설치
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    && rm -rf /var/lib/apt/lists/*

# 애플리케이션 JAR 파일 복사
COPY build/libs/api.jar /api.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/api.jar"]
