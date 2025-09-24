FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

RUN echo "=== build/libs 폴더 내용 ==="
RUN ls -la /app/build/libs/
RUN echo "=== 모든 jar 파일 찾기 ==="
RUN find /app -name "*.jar" -type f

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]