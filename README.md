# 💰 Finance Manager (Fullstack Web App)

A complete personal finance tracker with a **Spring Boot 3** backend and a **React.js** frontend.

## 🚀 Key Features

- **Interactive Dashboard:** Real-time balance updates (Today, Week, Month).
- **Smart Categories:** Autocomplete suggestions based on transaction history.
- **Live Search:** Instant filtering of transaction history by description or category.
- **Full CRUD:** Create, Read, Update, and Delete transactions with a few clicks.
- **Responsive UI:** Fully optimized for both Desktop and Mobile devices.

## 🛠 Tech Stack

### Backend

- **Java 21** / **Spring Boot 3**
- **Spring Data JPA** & **PostgreSQL** (Dockerized)
- **Swagger/OpenAPI** for interactive documentation.

### Frontend

- **React.js** (Functional Components & Hooks)
- **Axios** for seamless API integration.
- **CSS3 Custom Design** with Responsive Media Queries.

## 📂 Project Structure

The repository is divided into two main parts:

- **`/demo`** — ☕ **Java Backend**: Spring Boot application, REST API, and database logic.
- **`demo/frontend`** — ⚛️ **React Frontend**: The user interface, state management, and API integration.

## ⚙️ Getting Started

### 1. Database & Backend

```bash
# Start the PostgreSQL database
docker-compose up -d

# Run the Spring Boot application
./mvnw spring-boot:run


```

👉 Swagger UI: http://localhost:8080/swagger-ui/index.html

### 2. Frontend

cd frontend

npm install

npm start

👉 Web Interface: http://localhost:3000

### 🏗 Project Structure

/src - Java Spring Boot source code (Controller, Service, Repository layers).

/frontend - React.js application source code.

docker-compose.yml - Database configuration.
