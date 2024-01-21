FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /ms-order
COPY --from=build target/*.jar ms-order.jar
EXPOSE 0

ENTRYPOINT ["java", "-jar", "ms-order.jar"]