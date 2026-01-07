FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
 
# If pom.xml is inside src/
COPY src/pom.xml ./pom.xml
RUN mvn -q -DskipTests dependency:go-offline
 
COPY src/src ./src
RUN mvn -q -DskipTests clean package
 
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar /app/app.jar"]
