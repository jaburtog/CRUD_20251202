# Users CRUD Application

A complete CRUD (Create, Read, Update, Delete) application for managing users, built with **Java 21**, **Jakarta EE 10**, and **Clean Architecture** principles. The application uses **PostgreSQL** as the database and runs on **Liberty** application server.

## 🏗️ Architecture

This application follows **Clean Architecture** principles with clear separation of concerns:

### Layers

1. **Domain Layer** (`domain/`)
   - `entity/` - Core business entities (User)
   - `port/` - Repository interfaces (contracts)
   - Contains the core business logic and rules

2. **Use Case Layer** (`usecase/`)
   - Contains application business logic
   - Orchestrates domain objects
   - Implements CRUD operations with validation

3. **Infrastructure Layer** (`infrastructure/`)
   - `persistence/` - JPA repository implementations
   - `config/` - CDI configuration and producers
   - Implements domain ports

4. **Presentation Layer** (`presentation/`)
   - `rest/` - REST API endpoints (JAX-RS resources)
   - Handles HTTP requests/responses

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose (for PostgreSQL)
- Liberty Application Server (or Docker)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/jaburtog/CRUD_20251202.git
cd CRUD_20251202
```

### 2. Start PostgreSQL Database

Using Docker Compose:

```bash
docker-compose up -d
```

This will start a PostgreSQL instance with:
- Database: `usersdb`
- User: `postgres`
- Password: `postgres`
- Port: `5432`

The database will be automatically initialized with the schema from `database/init.sql`.

### 3. Build the Application

```bash
mvn clean package
```

This will create a WAR file: `target/users-crud.war`

### 4. Deploy to Liberty

#### Option A: Using Liberty Maven Plugin

Add the Liberty Maven plugin to `pom.xml` or manually deploy the WAR to your Liberty server.

#### Option B: Manual Deployment

1. Copy `target/users-crud.war` to your Liberty server's `dropins` directory
2. Copy the PostgreSQL JDBC driver to Liberty's shared resources:
   ```bash
   mkdir -p <LIBERTY_HOME>/usr/shared/resources/postgresql
   cp postgresql-*.jar <LIBERTY_HOME>/usr/shared/resources/postgresql/
   ```
3. Copy `src/main/liberty/config/server.xml` to your Liberty server configuration
4. Set environment variables for database connection:
   ```bash
   export DB_HOST=localhost
   export DB_PORT=5432
   export DB_NAME=usersdb
   export DB_USER=postgres
   export DB_PASSWORD=postgres
   ```
5. Start Liberty server

### 5. Access the Application

The application will be available at: `http://localhost:9080`

## 📡 API Endpoints

Base URL: `http://localhost:9080/api`

### Create User
```bash
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "active": true
}
```

### Get All Users
```bash
GET /api/users
```

### Get Active Users Only
```bash
GET /api/users?active=true
```

### Get User by ID
```bash
GET /api/users/{id}
```

### Get User by Email
```bash
GET /api/users/email/{email}
```

### Update User
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "phone": "+0987654321",
  "active": true
}
```

### Delete User
```bash
DELETE /api/users/{id}
```

## 🧪 Example Requests

### Create a new user:
```bash
curl -X POST http://localhost:9080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "email": "alice@example.com",
    "phone": "+1122334455",
    "active": true
  }'
```

### Get all users:
```bash
curl http://localhost:9080/api/users
```

### Update a user:
```bash
curl -X PUT http://localhost:9080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Smith",
    "email": "alice.smith@example.com",
    "phone": "+1122334455",
    "active": true
  }'
```

### Delete a user:
```bash
curl -X DELETE http://localhost:9080/api/users/1
```

## 🗄️ Database Schema

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
```

## 🔧 Configuration

### Database Configuration

The database connection is configured via environment variables in `server.xml`:

- `DB_HOST` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 5432)
- `DB_NAME` - Database name (default: usersdb)
- `DB_USER` - Database user (default: postgres)
- `DB_PASSWORD` - Database password (default: postgres)

### JPA Configuration

JPA is configured in `src/main/resources/META-INF/persistence.xml` with:
- Hibernate as the JPA provider
- PostgreSQL dialect
- Auto DDL update (for development)
- SQL logging enabled

## 📦 Technology Stack

- **Java 17** - Programming language
- **Jakarta EE 10** - Enterprise Java platform
  - Jakarta Persistence (JPA 3.1)
  - Jakarta RESTful Web Services (JAX-RS 3.1)
  - Jakarta Contexts and Dependency Injection (CDI 4.0)
  - Jakarta Validation (Bean Validation 3.0)
- **PostgreSQL 16** - Relational database
- **Liberty** - Application server
- **Maven** - Build tool
- **Docker** - Containerization for PostgreSQL

## 🏛️ Clean Architecture Benefits

1. **Independence**: Business logic is independent of frameworks, UI, and database
2. **Testability**: Business rules can be tested without external dependencies
3. **Flexibility**: Easy to swap implementations (e.g., change database or web framework)
4. **Maintainability**: Clear separation of concerns makes the code easier to understand and modify

## 📝 Business Rules

- Email addresses must be unique
- User names must be between 2 and 100 characters
- Email validation is enforced
- Timestamps are automatically managed
- Users can be marked as active/inactive

## 🛠️ Development

### Project Structure
```
CRUD_20251202/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/users/crud/
│       │       ├── domain/
│       │       │   ├── entity/
│       │       │   │   └── User.java
│       │       │   └── port/
│       │       │       └── UserRepository.java
│       │       ├── usecase/
│       │       │   └── UserUseCase.java
│       │       ├── infrastructure/
│       │       │   ├── persistence/
│       │       │   │   └── UserRepositoryJPA.java
│       │       │   └── config/
│       │       │       └── RepositoryProducer.java
│       │       └── presentation/
│       │           └── rest/
│       │               ├── RestApplication.java
│       │               └── UserResource.java
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       ├── liberty/
│       │   └── config/
│       │       └── server.xml
│       └── webapp/
│           └── WEB-INF/
│               └── beans.xml
├── database/
│   └── init.sql
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 📄 License

This project is licensed under the MIT License.

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📞 Support

For issues, questions, or contributions, please open an issue in the GitHub repository.
