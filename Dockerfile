# Step 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /target/hms-1.0-SNAPSHOT.jar hms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hms.jar"]