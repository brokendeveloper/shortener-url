# URL Shortener

This project is a simple URL shortener created as a technical test for TDS company.

## Description

The application takes a long URL and generates a shorter, unique alias. When a user accesses the short alias, they are redirected to the original long URL.

## Technologies Used

*   **Java 17**
*   **Spring Boot 3.5.3**
*   **Spring Web**
*   **Spring Data JPA**
*   **Maven**
*   **PostgreSQL**
*   **Lombok**

## How to Run

1.  **Prerequisites:**
    *   Java 17 or later
    *   Maven
    *   A running PostgreSQL instance

2.  **Configuration:**
    *   In the `src/main/resources` directory, you'll find a file named `application.example.yml`.
    *   Create a copy of this file and rename it to `application.yml`.
    *   Open the new `application.yml` file and update the `spring.datasource` properties (url, username, password) to match your PostgreSQL configuration.

3.  **Execution:**
    *   Clone the repository.
    *   Run the application using the following Maven command in the project root:
        ```bash
        ./mvnw spring-boot:run
        ```

## Branching Model

The `main` branch is used for the initial setup of this project. All ongoing development is done in the `developer` branch.

## API Endpoints

(Details of the API endpoints will be added here.)
