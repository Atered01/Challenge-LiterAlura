# LiterAlura

## Project Description

LiterAlura is a console application that allows users to search for and register books and authors from the Gutendex API. The application provides an interactive menu to explore the book catalog, list registered authors and books, and filter information by year or language.

## Features

* **Search Book by Title:** Searches for a book in the Gutendex API and stores it in the local database if it's not already registered.
* **List Registered Books:** Displays all the books that have been saved to the database.
* **List Registered Authors:** Shows all authors stored in the database.
* **List Authors Alive in a Given Year:** Allows the user to input a year and displays the authors who were alive during that period.
* **List Books by Language:** Filters and displays the books available in a specific language (Portuguese, English, French, Spanish, or Italian).

## Technologies Used

* **Java 24:** The version of the programming language used in the project.
* **Spring Boot:** A framework for rapidly and simply developing Java applications.
* **Spring Data JPA:** Facilitates the implementation of repositories for data access.
* **PostgreSQL:** The relational database management system used for data storage.
* **Maven:** A tool for build automation and dependency management.
* **Jackson:** A library for processing JSON in Java.

## How to Run the Project

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/challenge-literalura.git](https://github.com/your-username/challenge-literalura.git)
    ```

2.  **Set up environment variables:**
    In the `src/main/resources/application.properties` file, configure the following variables with your PostgreSQL database credentials:
    ```properties
    DB_HOST=your-db-host
    DB_USER=your-db-user
    DB_PASSWORD=your-db-password
    ```

3.  **Run the application:**
    Use Maven to compile and run the project:
    ```bash
    mvn spring-boot:run
    ```

## API Used

The project uses the public [Gutendex](https://gutendex.com) API to retrieve book information.