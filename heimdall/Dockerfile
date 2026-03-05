FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

RUN ./mvnw -q -B dependency:go-offline

COPY src ./src

RUN ./mvnw -q -B clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN mkdir -p /data/h2

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]
