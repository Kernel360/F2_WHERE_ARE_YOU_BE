
# badminton-api Dockerfile

FROM amazoncorretto:17

LABEL authors="hit team"

COPY build/libs/api.jar /docker-springboot.jar

ENTRYPOINT ["java", "-jar", "/docker-springboot.jar"]