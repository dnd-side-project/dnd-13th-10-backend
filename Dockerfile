# Multi-stage build를 사용하여 이미지 크기 최적화
FROM openjdk:17-jdk-slim as builder

WORKDIR /app

# Gradle 캐시를 위해 먼저 dependency 파일들만 복사
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle.properties* ./

# Gradle wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 다운로드 (캐시 레이어)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드 (테스트 제외)
RUN ./gradlew build -x test --no-daemon

# 런타임 이미지
FROM openjdk:17-jdk-slim

# 애플리케이션 실행을 위한 사용자 생성 (보안)
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 소유권 변경
RUN chown spring:spring app.jar

# 포트 노출
EXPOSE 8080

# 비루트 사용자로 실행
USER spring

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]