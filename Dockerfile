FROM eclipse-temurin:22-jdk-alpine

COPY target/observability.jar /observability.jar

ENTRYPOINT ["java", "-jar", "/observability.jar"]