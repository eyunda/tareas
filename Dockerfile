FROM openjdk:17-jdk-slim

COPY target/taskmanager.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
