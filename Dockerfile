# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Copy jar built by Gradle
COPY build/libs/rating-0.0.1-SNAPSHOT.jar app.jar

# Expose port
ENV PORT 8080
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]
