# badminton-batch Dockerfile

FROM amazoncorretto:17

LABEL authors="hit team"

COPY build/libs/batch.jar /batch.jar

ENTRYPOINT ["java", "-jar", "/batch.jar"]