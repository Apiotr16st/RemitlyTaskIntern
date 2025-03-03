FROM openjdk:17-jdk-slim

WORKDIR /task

COPY build/libs/*.jar task.jar
COPY src/main/resources/ /app/

EXPOSE 8080

CMD ["java", "-jar", "task.jar"]
