# Finance Manager API

A robust and scalable RESTful API built with Spring Boot for tracking personal transactions and managing budgets.

## 🚀 Key Features

- **Full CRUD Support**: Create, Read, Update, and Delete transactions.
- **Data Validation**: Server-side validation for transaction amounts and descriptions.
- **Automatic Formatting**: Category normalization (auto-uppercase) and description trimming.
- **Interactive Documentation**: Integrated Swagger UI for easy API testing.

## 🛠 Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3
- **Persistence:** Spring Data JPA
- **Database:** PostgreSQL (Running in Docker)
- **DevOps:** Docker & Docker Compose
- **Documentation:** OpenAPI / Swagger UI

## 📋 Prerequisites

- **Java 21** or higher
- **Docker Desktop** installed and running

## ⚙️ Getting Started

1. **Clone the repository:**

   ```bash
   git clone [https://github.com/your-username/finance-manager-backend.git](https://github.com/your-username/finance-manager-backend.git)
   cd finance-manager-backend
   ```

2. **Spin up the database:**

docker-compose up -d

3. **Run the application:**

./mvnw spring-boot:run

## API Documentation

Once the application is running, you can access the interactive Swagger UI at:
👉 http://localhost:8080/swagger-ui/index.html

## 🏗 Project Architecture

The project follows a layered architecture pattern:

Controller Layer: Handles HTTP requests and REST mapping.

Service Layer: Contains business logic and data validation.

Repository Layer: Manages database interactions via Spring Data JPA.

Entity Layer: Defines the database schema using JPA annotations.
