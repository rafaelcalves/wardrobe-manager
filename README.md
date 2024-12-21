# Wardrobe Manager

A personal project designed as a sandbox for exploring and implementing various software development practices and technologies. While currently focusing on basic wardrobe management functionality, the project serves as a continuous learning platform, free from company-specific constraints.

Current features include basic CRUD operations for managing clothing items, but the project is actively evolving. Check out our [epics board](https://github.com/users/rafaelcalves/projects/2/views/7) for upcoming features and improvements.

## 🚀 Features

- **Clothing Management**: Create, update, delete, and search clothing pieces
- **Brand Organization**: Manage and categorize clothing by brands
- **Category System**: Organize pieces into customizable categories
- **RESTful API**: Well-documented API endpoints for seamless integration
- **Clean Architecture**: Follows domain-driven design principles for maintainability

## 🛠️ Technology Stack

- **Backend**: Spring Boot
- **Database**: MongoDB 
- **Build Tool**: Gradle 
- **Testing**: JUnit with Testcontainers for integration tests
- **Documentation**: SpringDoc/OpenAPI
- **Containerization**: Docker Compose

## 🏗️ Architecture

The project follows Clean Architecture principles with three main layers:

- **Domain Layer**: Core business entities and rules
- **Application Layer**: Use cases and business logic
- **Infrastructure Layer**: Controllers, repositories, and external interfaces

## 🚦 Prerequisites

- Java 21
- An IDE (VSCode or IntelliJ IDEA)
- Docker and Docker Compose

## 🔧 Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rafaelcalves/wardrobe-manager.git
   cd wardrobe-manager
   ```

2. **IDE Setup**

   **VSCode**:
   1. Install the following extensions:
      - Extension Pack for Java
      - Spring Boot Extension Pack
      - Gradle for Java
   2. Open the project folder in VSCode
   3. Wait for the Java projects to be imported
   4. The project includes a pre-configured `.vscode/launch.json` with a "Spring Boot-Local" configuration that:
      - Sets up the main class
      - Configures the local profile
      - Sets up environment variables
   5. Click on "Run and Debug" (Ctrl+Shift+D)
   6. Select "Spring Boot-Local" from the dropdown
   7. Click the play button or press F5

   **IntelliJ IDEA**:
   1. Go to `File -> Open`
   2. Select the project's root directory
   3. Wait for the Gradle project to be imported
   4. The project includes a pre-configured run configuration in `.idea/runConfigurations/Application.xml` that:
      - Sets up the Spring Boot configuration
      - Configures the local profile
      - Sets the main class
   5. Select "Application" from the run configurations dropdown
   6. Click the green play button to run

3. **Command Line Setup**
   ```bash
   # Build the project using the Gradle wrapper
   ./gradlew build

   # Run with local profile (includes Docker Compose integration)
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```

The application uses Spring Boot's Docker Compose integration in the local profile, which will automatically:
- Start MongoDB container
- Configure the necessary connections
- Set up the development environment

## 📚 API Documentation

Once the application is running, access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🧪 Running Tests

```bash
./gradlew test
```

Make sure Docker is running before executing tests, as Testcontainers is used for integration testing.

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── br/com/correa/wardrobemanager/
│   │       ├── application/    # Use cases and business rules
│   │       ├── domain/        # Core entities and business logic
│   │       └── infra/         # Controllers, repositories, and external interfaces
│   └── resources/
└── test/
    └── java/                  # Test classes
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License - see the [LICENSE](LICENSE) file for details.

## 🔗 Related Projects

- [Wardrobe Manager Web](https://github.com/rafaelcalves/wardrobe-manager-web) - Frontend application