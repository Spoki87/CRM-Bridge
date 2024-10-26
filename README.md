# CRM Sync Service

The **CRM Sync Service** is a Spring Boot application designed to synchronize data from multiple modules (e.g., Leads, Products, Companies) with an external CRM system, such as Zoho CRM. This service provides a scalable structure for data integration with features for asynchronous processing and detailed synchronization reporting. The current structure is a baseline, requiring further configuration of data models and mapping.

## Technologies

This project uses the following stack:

- **Java 17**: Programming language used to build the application.
- **Spring Boot**: Framework to streamline configuration and application startup.
- **Spring Data JPA**: Handles object-relational mapping for database interactions.
- **PostgreSQL**: Database used to store entities and sync reports.
- **Lombok**: Reduces boilerplate code for models and data classes.

## Project Structure

The project is organized into key modules:

- **Controllers**: Handles HTTP requests and API endpoints.
- **Services**: Contains core business logic and CRM synchronization processes.
- **Repositories**: Manages database interactions for different entities.
- **Models**: Defines database entities and DTOs.
- **Reports**: Manages sync reports for tracking each synchronization's success or failure.

## Prerequisites and Configuration

The project requires configuration of:

1. **Data Models**: Define models in the `model` package for all entities you plan to synchronize with Zoho (e.g., Leads, Products).
2. **Zoho Data Models**: Ensure you have DTOs that represent Zoho's data structure.
3. **Mappers**: Define mappers to convert between application models and Zoho DTOs, enabling accurate data synchronization.

This application serves as a base structure. Custom data models and mappers should be implemented based on specific requirements for Zoho CRM.

## Installation

### Prerequisites

Ensure you have the following installed:

- Java 17+
- PostgreSQL
- Maven

### Setup


1. **Configure the Database**:
   Update `src/main/resources/application.properties` with your PostgreSQL credentials:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=create-drop
    ```

2. **Configure Zoho Tokens**:
   Ensure that all necessary Zoho tokens are specified in `application.properties` for authentication:

   ```properties
   zoho.api.client_id=your_client_id
   zoho.api.client_secret=your_client_secret
   zoho.api.refresh_token=your_refresh_token
