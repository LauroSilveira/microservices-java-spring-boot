FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /api-gateway
COPY --from=build target/*.jar api-gateway.jar
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]