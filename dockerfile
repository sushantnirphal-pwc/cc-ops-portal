# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (go offline)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source code and build without running tests
COPY src ./src
RUN mvn -B -DskipTests clean package

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar from Stage 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
