# Schritt 1: Das Programm bauen (Build-Stage)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Schritt 2: Das Programm ausführen (Run-Stage)
# Wir nutzen hier ein extrem stabiles Image von Eclipse Temurin
FROM eclipse-temurin:17-jre
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]