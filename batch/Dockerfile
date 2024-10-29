# badminton-batch Dockerfile

FROM amazoncorretto:17

LABEL authors="hit team"

COPY build/libs/badminton-batch.jar /docker-springboot-batch.jar

ENTRYPOINT ["java", "-jar", "/docker-springboot-batch.jar"]