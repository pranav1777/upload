# ---- Stage 1: Build ----
FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

# Copy everything
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the jar
RUN ./mvnw clean package -DskipTests

# ---- Stage 2: Run ----
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set Spring profile
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
