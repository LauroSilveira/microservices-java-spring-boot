FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /service-discovery
COPY --from=build target/*.jar service-discovery.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-discovery.jar"]