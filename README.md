# 📚 Library API

This is a study project to build and improve backend development skills using Java and Spring Boot. The API simulates a library system, providing endpoints for managing books, users, loans, and more.

## 🚀 Technologies and Tools

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- OAuth2 & Social Login
- Docker
- AWS (EC2, RDS)
- Swagger / OpenAPI
- JUnit / Integration Tests

## ✅ Project Goals (Checklist)

### - Core Development
- [X] Create a RESTful API for library management
- [ ] Implement advanced API modeling and design
- [ ] Use DTOs, exception handling, and validation best practices

### - Database & Persistence
- [X] Setup JPA/Hibernate with relational database
- [X] Use PostgreSQL locally
- [ ] Use PostgreSQL via AWS RDS
- [X] Seed initial data with test fixtures
- [X] Understand and apply JPA Entity Lifecycle (Transient, Persistent, Detached, Removed)
- [X] Study database transaction behavior and isolation levels ️(ACID, Transactions, Lock, Isolation Levels)

### - Security
- [ ] Apply authentication and authorization using Spring Security
- [ ] Implement OAuth2 flow with JWT tokens
- [ ] Add social login with Google

### - Cloud Deployment
- [ ] Deploy the application to AWS using EC2
- [ ] Use AWS RDS for database hosting

### - Containers
- [ ] Containerize the application using Docker
- [ ] Run services and databases in isolated containers

### - Testing
- [X] Write unit tests with JUnit and Mockito
- [ ] Implement integration tests for API endpoints

### - API Documentation
- [ ] Document all endpoints using Swagger and OpenAPI

## 📌 Project Status

> Ongoing – Features and structure are being added gradually for learning and experimentation purposes.

## 🧠 Learning Focus

This project is aimed to strengthen knowledge in:

- Spring ecosystem architecture
- Clean API design and best practices
- Secure and scalable backend applications
- Dockerized deployments and cloud infrastructure
- JPA Entity States (`Transient`, `Persistent`, `Detached`, `Removed`) ️

## How to Run
### 🐳 Running PostgreSQL + pgAdmin (Docker Setup)

This project includes a local PostgreSQL database and pgAdmin UI using Docker. 

Below are the exact steps to start 
everything and connect pgAdmin to your database.

#### 1️⃣ Start the containers

```docker-compose up -d```

This will start:

- PostgreSQL at localhost:5432
- pgAdmin at localhost:15432

#### 2️⃣ Access pgAdmin

Open in your browser: http://localhost:15432

Login:
- Email: ```admin@admin.com```
- Password: ```admin```

#### 3️⃣ Register the PostgreSQL server inside pgAdmin

pgAdmin does not auto-detect Docker containers.
You must register the server manually:

#### Steps

1. Right-click **Servers**
2. Select **Register → Server**
3. In the **General** tab:
    - Set **Name:** `librarydb` (any name is fine)
4. In the **Connection** tab:
    - **Host:** `librarydb`
    - **Port:** `5432`
    - **Username:** `postgres`
    - **Password:** `postgres`
    - **Database:** `library`

---

## Contributions Welcome

Feel free to fork or contribute. This is a learning sandbox to explore real-world backend scenarios.