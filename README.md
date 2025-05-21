# Quiz App - Java Spring Boot

## Overview

A simple Quiz App using Java Spring Boot. Users can take quizzes, submit answers, and see scores. Admins can manage quiz questions.

## Features
- **JWT Authentication**:
  - Secure token-based authentication
  - Role-based access (USER/ADMIN)
- **Quiz Functionality**:
  - Take quizzes and view scores
  - Submit answers via REST API
- **Admin Panel**:
  - CRUD operations for quiz questions
  - User management

## Project Structure
- **quiz-app**
  - `src/`
    - `main/java/com/example/quizapp/`
      - `config/` - Security and application configuration
      - `controllers/` - API endpoints
      - `dao/` - Data access objects
      - `entities/` - Database models
      - `jwt/` - JWT authentication components
      - `oAuth2/` - OAuth2 login handlers
      - `payload/` - Request/response DTOs
      - `services/` - Business logic
    - `resources/`
      - `templates/` - Frontend views (if any)
      - Configuration files:
        - `application.properties`
        - `application-h2.properties`
        - `application-postgre.properties`
        - `schema.sql`
  - Main class: `QuizappApplication.java`

## Setup

### Requirements
- Java 17+
- Maven
- PostgreSQL/MySQL

### Run the Project
1. Clone the repo:
git clone https://github.com/binabina0/quiz-app.git && cd quiz-app

2. Build and run:
mvn spring-boot:run

3. API available at `http://localhost:8080`

## API Endpoints

### Authentication Controller (`/api/auth`)
| Method | Endpoint          | Role  | Description                          |
|--------|-------------------|-------|--------------------------------------|
| POST   | `/register`       | PUBLIC| Register new user                    |
| POST   | `/login`          | PUBLIC| Login with credentials (JWT token)   |
| POST   | `/refresh-token`  | PUBLIC| Refresh expired JWT token            |
| GET    | `/oauth-success`  | PUBLIC| OAuth2 login success redirect        |

### Quiz Controller (`/quiz`)
| Method | Endpoint          | Role  | Description                          |
|--------|-------------------|-------|--------------------------------------|
| POST   | `/create`         | ADMIN | Create new quiz                      |
| GET    | `/get/{id}`       | USER  | Get quiz questions                   |
| POST   | `/submit/{id}`    | USER  | Submit quiz answers                  |

### Question Controller (`/question`)
| Method | Endpoint              | Role  | Description                          |
|--------|-----------------------|-------|--------------------------------------|
| GET    | `/allQuestions`       | ADMIN | List all questions                  |
| GET    | `/category/{category}`| ADMIN | Filter by category                  |
| POST   | `/add`                | ADMIN | Add new question                    |
| PUT    | `/update`             | ADMIN | Update existing question            |
| DELETE | `/delete/{id}`        | ADMIN | Delete question                     |

## Security Setup

### Authentication Flow

sequenceDiagram
User->>+Frontend: Login (username/password)
Frontend->>+Backend: Auth Request
Backend->>+DB: Verify Credentials
DB-->>-Backend: User Data
Backend-->>-Frontend: JWT Token
Frontend->>+Backend: API Requests (with JWT)
Backend-->>-Frontend: Quiz Data


### Testing Authentication

1. Get JWT Token:
   curl -X POST http://localhost:8080/api/auth/login
   -H "Content-Type: application/json"
   -d '{"username":"user1", "password":"user123"}'


2. Access Protected Endpoints:
   curl -X GET http://localhost:8080/api/quizzes
   -H "Authorization: Bearer YOUR_JWT_TOKEN"


## Future Enhancements
- Social login
- Timed quizzes
- Multimedia questions

## License
MIT License

## Contributors
- albina
