# Стадия сборки (Gradle собирает jar внутри контейнера)
FROM gradle:8.10-jdk17-alpine AS builder

WORKDIR /home/gradle/project

# Копируем gradle-wrapper и конфиги
COPY build.gradle settings.gradle gradlew gradle/ ./
# Копируем исходники
COPY src ./src

# Собираем jar
RUN ./gradlew clean bootJar --no-daemon

# -------------------- Рантайм-слой --------------------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Копируем собранный jar из стадии builder
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]
