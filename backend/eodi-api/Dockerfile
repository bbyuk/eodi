# =========================
# 1. Build Stage
# =========================
FROM gradle:8.6-jdk17 AS builder
WORKDIR /app

# Gradle 캐시 최적화
COPY build.gradle settings.gradle ./
COPY gradle gradle
RUN gradle dependencies --no-daemon || true

# 소스 복사
COPY src src

# 빌드
RUN gradle build -x test --no-daemon


# =========================
# 2. Runtime Stage
# =========================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트
EXPOSE 8080

# JVM 옵션
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
