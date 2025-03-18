# Quiz App - Java Spring Boot

## Overview

A simple Quiz App using Java Spring Boot. Users can take quizzes, submit answers, and see scores. Admins can manage quiz questions.

## Features
- Take quizzes and view scores.
- Manage questions (Admin only).
- REST API for quiz functionality.

## Project Structure
- **quiz-app**
  - `src/`
    - `main/java/com/example/quizapp/`
      - `controller/` (Handles requests)
      - `service/` (Business logic)
      - `entities/` (Database access)
      - `dao/` (Data models)
  - `application.properties` (Configuration)
  - `pom.xml` (Maven dependencies)
  - `README.md` (Documentation)
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

| Method | Endpoint               | Action                |
|--------|------------------------|-----------------------|
| GET    | `/api/quizzes`         | Get all questions     |
| POST   | `/api/quizzes`         | Add question (Admin)  |
| PUT    | `/api/quizzes/{id}`    | Update question (Admin) |
| DELETE | `/api/quizzes/{id}`    | Delete question (Admin) |
| POST   | `/api/quizzes/submit`  | Submit answers        |

## Future Enhancements
- User authentication
- Timed quizzes
- Multimedia questions

## License
MIT License

## Contributors
- albina
