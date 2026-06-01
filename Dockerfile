# Stage 1: Build the JAR file inside a clean Java 21 environment
FROM maven:3.9.7-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the compiled JAR file
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/resume-radar-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
