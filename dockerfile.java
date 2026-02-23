# Stage 1: Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy only the JAR file
COPY --from=builder /build/target/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
