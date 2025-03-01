FROM openjdk:17-jdk-slim

WORKDIR /task

COPY build/libs/*.jar task.jar

EXPOSE 8080

CMD ["java", "-jar", "task.jar"]
