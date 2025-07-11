# Sport Ink EXE Product

This repository contains the source code for the Sport Ink EXE Product, a system designed for managing sports-related activities. The project is divided into two main software components, located in the `sport-ink-exe-software` directory.

## Project Structure

The repository is organized as follows:

```
.
├── sport-ink-exe-software
│   ├── sport-ink-system
│   └── sport-ink-system-alt
└── README.md
```

### 1. `sport-ink-system`

This system appears to follow a **Hexagonal Architecture (Ports and Adapters)** pattern. The goal is to isolate the core business logic from external concerns.

-   **`core/domain`**: Contains the core business models and entities.
-   **`core/application-service`**: Implements the application's use cases.
-   **`adapter`**: Contains adapters for interacting with external systems (e.g., REST controllers, database gateways).
-   **`infrastructure`**: Holds the concrete implementations for infrastructure concerns like caching, database access, and messaging queues.
-   **`application`**: The main entry point of the Spring Boot application.

### 2. `sport-ink-system-alt`

This is a Spring Boot application that follows a more traditional, feature-oriented layered architecture. The code is organized into modules based on functionality.

Key modules include:
-   **`administration`**: For administrative tasks like managing sports.
-   **`booking`**: Handles booking of playfields.
-   **`chatting`**: Real-time chat functionality.
-   **`playfield`**: Manages venues and playfields.
-   **`playtime`**: Manages playtime sessions.
-   **`user`**: User authentication and management.
-   **`shared`**: Contains common code, configurations, and utilities shared across different modules.

Each module typically contains:
-   **`controller`**: Exposes REST APIs.
-   **`domain`**: Contains DTOs and entities.
-   **`repository`**: Data access layer.
-   **`service`**: Implements the business logic.

## Getting Started

### Prerequisites

Both `sport-ink-system` and `sport-ink-system-alt` require the following software to be installed:

-   Java Development Kit (JDK) 17 or later
-   PostgreSQL
-   Redis
-   RabbitMQ

### Environment Variables

Before running the applications, you must set up the required environment variables. You can set these as system environment variables or, for easier management, create a `.env` file in the root directory of each application (`sport-ink-system` and `sport-ink-system-alt`) and populate it with the necessary values.

#### For `sport-ink-system`

This application requires the following environment variables. Create a `.env` file inside the `sport-ink-exe-software/sport-ink-system` directory with the following content:

```env
# PostgreSQL Database
DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_db_name
DATASOURCE_USERNAME=your_db_user
DATASOURCE_PASSWORD=your_db_password

# JWT Configuration
JWT_SECRET_KEY=your_super_secret_key
ACCESS_TOKEN_EXPIRATION_IN_MS=3600000
REFRESH_TOKEN_EXPIRATION_IN_MS=86400000
EMAIL_VERIFICATION_TOKEN_EXPIRATION_IN_MS=86400000

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672

# Mail Server Configuration
MAIL_HOST=smtp.example.com
MAIL_PORT=587
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
MAIL_PROTOCOL=smtp
```

#### For `sport-ink-system-alt`

This application requires the following environment variables. Create a `.env` file inside the `sport-ink-exe-software/sport-ink-system-alt` directory with the following content:

```env
# Server Port
PORT=8080

# PostgreSQL Database
DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_alt_db_name
DATASOURCE_USERNAME=your_db_user
DATASOURCE_PASSWORD=your_db_password

# JWT Configuration
JWT_SECRET_KEY=your_super_secret_key
ACCESS_TOKEN_EXPIRATION_IN_MS=3600000
REFRESH_TOKEN_EXPIRATION_IN_MS=86400000
EMAIL_VERIFICATION_TOKEN_EXPIRATION_IN_MS=86400000

# Google OAuth2
FIREBASE_SERVICE_ACCOUNT_ID=your_firebase_service_account_id
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672

# Mail Server Configuration
MAIL_HOST=smtp.example.com
MAIL_PORT=587
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
MAIL_PROTOCOL=smtp

# Vietmap API
VIETMAP_API_KEY=your_vietmap_api_key
```

### Running the Applications

#### Running `sport-ink-system`

1.  Navigate to the `sport-ink-exe-software/sport-ink-system` directory.
2.  Create and configure your `.env` file as described above.
3.  Run the application using the Gradle wrapper:

    For Windows:
    ```bash
    gradlew.bat bootRun
    ```

    For macOS/Linux:
    ```bash
    ./gradlew bootRun
    ```

#### Running `sport-ink-system-alt`

1.  Navigate to the `sport-ink-exe-software/sport-ink-system-alt` directory.
2.  Create and configure your `.env` file as described above.
3.  Run the application using the Gradle wrapper:

    For Windows:
    ```bash
    gradlew.bat bootRun
    ```

    For macOS/Linux:
    ```bash
    ./gradlew bootRun
    ```