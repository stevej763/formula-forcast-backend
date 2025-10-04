# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

# Copy Gradle wrapper and build files first (layer caching)
COPY gradlew settings.gradle build.gradle gradle.properties* ./
COPY gradle ./gradle

# Ensure wrapper is executable
RUN chmod +x gradlew

# Download dependencies (no source yet for better cache)
RUN ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

# Copy source
COPY src ./src

# Build jar (skip tests)
RUN ./gradlew clean build -x test --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy built jar (rename to app.jar)
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# Non-root user
RUN useradd -u 10001 spring && chown -R spring:spring /app
USER spring

# Default environment
ENV SPRING_PROFILES_ACTIVE=demo
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Expose default Spring Boot port
EXPOSE 8080

# Optional health check (adjust path if actuator enabled)
# HEALTHCHECK --interval=30s --timeout=3s CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]