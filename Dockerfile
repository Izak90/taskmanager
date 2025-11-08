FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/taskmanager.jar .
ENTRYPOINT ["java", "-jar", "taskmanager.jar"]