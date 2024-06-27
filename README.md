# TestManagement
# Multiple Choice Question Test Management API

This Spring Boot application manages multiple-choice questions for tests, providing CRUD operations through RESTful API endpoints.

## Overview

The project implements a simple CRUD application using Spring Boot and Spring Data JPA. It allows users to create, read, update, and delete multiple-choice questions for tests.

## API Endpoints

### Controller Layer

The controller layer handles HTTP requests and routes them to corresponding service methods.

- **Create a question**: `POST /questions`
- **Retrieve all questions**: `GET /questions/get-all`
- **Retrieve a specific question by ID**: `GET /questions/{questionId}`
- **Update a specific question by ID**: `PUT /questions{questionId}`
- **Delete a specific question by ID**: `DELETE /questions/{questionId}`

### Service Layer

The service layer contains business logic for managing multiple-choice questions.

Service Methods:
- `saveQuestion(MultipleChoiceQuestion question)`: Creates a new question.
- `getAllQuestionsData()`: Retrieves all questions.
- `getQuestionById(Integer questionId)`: Retrieves a specific question by ID.
- `updateQuestion(Integer questionId, MultipleChoiceQuestion updatedQuestion)`: Updates a specific question by ID.
- `deleteQuestion(Integer questionId)`: Deletes a specific question by ID.

### Repository Layer

The repository layer interfaces with the database using Spring Data JPA repositories to perform CRUD operations on `MultipleChoiceQuestion` entities.

### Model Layer

The model layer defines the structure of `MultipleChoiceQuestion` entities using JPA annotations. It includes attributes such as question text, options, correct answer, category, etc.

### Database Connectivity

The application uses Spring Data JPA and is configured via `application.properties` for database connectivity with PostgreSQL.

### Testing

Unit tests are implemented using JUnit 5 and Mockito, covering controller and service layer methods.

## Build Steps

To build and run the application locally:

1. Ensure you have JDK 21 installed on your machine.
2. Clone the repository:

   ```bash
   git clone https://github.com/Jayesh2026/TestManagement.git
   cd multiple-choice-question-api
3. Open the project in your preferred Integrated Development Environment (IDE) or navigate to the project directory using a terminal or command prompt.
4. Ensure that you have the build.gradle file in the project root directory. This file should contain the project configuration and dependencies.
5. Build the project using Gradle by running the following command in the project directory:

   ```bash
   ./gradlew build
