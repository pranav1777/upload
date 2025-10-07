# ---- Builder image ----
FROM openjdk:17-jdk-slim AS builder

# Set working directory
WORKDIR /app

# Copy project files and build the jar
COPY . .
# Use the maven wrapper inside the container; the wrapper script is Unix executable in the repo
RUN ./mvnw clean package -DskipTests

# ---- Run image ----
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Default env
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
