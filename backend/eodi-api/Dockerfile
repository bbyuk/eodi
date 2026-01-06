# =========================
# 1. Build Stage
# =========================
FROM gradle:8.6-jdk17 AS builder
WORKDIR /app

# ⭐ Gradle 옵션 (429 방지 핵심)
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.parallel=false -Dorg.gradle.workers.max=2"

# 1️⃣ 의존성 관련 파일만 먼저 복사 (캐시 핵심)
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle gradle

# 2️⃣ 의존성만 미리 다운로드 (실패해도 캐시 유지)
RUN gradle dependencies \
    --no-daemon \
    --no-parallel \
    --max-workers=2 \
    || true

# 3️⃣ 소스 복사
COPY src src

# 4️⃣ 실제 빌드
RUN gradle build -x test \
    --no-daemon \
    --no-parallel \
    --max-workers=2


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
