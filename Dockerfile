FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY build/libs/saluyustore-0.0.1.jar saluyu-service.jar
ENTRYPOINT ["java","-jar","/app/saluyu-service.jar"]