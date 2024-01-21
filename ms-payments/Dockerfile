FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /ms-payments
COPY --from=build target/*.jar ms-payments.jar
EXPOSE 0

ENTRYPOINT ["java", "-jar", "ms-payments.jar"]