# Task Manager API

A simple **Spring Boot REST API** for managing tasks, developed as part of a technical demonstration for a backend engineering interview.

This project showcases clean architecture, REST principles, and role-based access control with Spring Security.  
It’s designed to be lightweight, self-contained, and easily extendable to a cloud-native microservice.

---

## Features

- CRUD operations for `Task` entities (create, read, update, delete)
- In-memory database (H2)
- Role-based access control (USER / ADMIN)
- RESTful endpoints built with Spring Web
- Validation using Jakarta Validation API
- Simple unit test with JUnit & MockMvc
- Ready for CI/CD with GitHub Actions

---

## Tech Stack

| Layer                         | Technology                           |
|-------------------------------|--------------------------------------|
| **Language**                  | Java 17                              |
| **Framework**                 | Spring Boot 3.3                      |
| **Database**                  | H2 (in-memory)                       |
| **Persistence**               | Spring Data JPA                      |
| **Security**                  | Spring Security (HTTP Basic + Roles) |
| **Validation**                | Jakarta Bean Validation              |
| **Build Tool**                | Maven                                |
| **Testing**                   | JUnit 5, MockMvc                     |
| **CI/CD**                     | GitHub Actions                       |
| **Containerization (future)** | Docker & Kubernetes                  |

---

## API Endpoints

| Method | Endpoint    | Role        | Description            |
|--------|-------------|-------------|------------------------|
| GET    | /tasks      | USER, ADMIN | List all tasks         |
| GET    | /tasks/{id} | USER, ADMIN | Retrieve a single task |
| POST   | /tasks      | ADMIN       | Create a new task      |
| PUT    | /tasks/{id} | ADMIN       | Update existing task   |
| DELETE | /tasks/{id} | ADMIN       | Remove a task          |

## API Documentation
Swagger UI available at:
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Logging
Logs are configured via `application.yml`:
- Default: INFO
- Package `dk.jysk.taskmanager`: DEBUG

## How to Run

```bash
# Build and run the application
mvn spring-boot:run


