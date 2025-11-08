FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/taskmanager.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "taskmanager.jar"]