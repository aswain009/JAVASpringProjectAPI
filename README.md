# Lugnuts Automotive API (Spring Boot + MySQL)

This repository contains the Lugnuts Automotive REST API built with Spring Boot 3 (Java 17) and Gradle. It uses MySQL as the backing database, populated with the classic "project_data" sample schema/data.

The project is already configured to:
- Build and run only the Lugnuts API (main class: `com.example.lugnutsautomotive.LugnutsAutomotiveApplication`).
- Use the `mysql` profile by default (no extra flags needed).
- Connect to a local MySQL server using user `root` and an empty password by default.
- Target the `project_data` database and create it automatically if it does not exist.


## Prerequisites
- Java 17+
- Local MySQL Server 5.7+ or 8.x
- MySQL client (`mysql`) for importing data
- Bash-compatible shell (for the Gradle wrapper)

Verify Java:

```
java -version
```

Verify MySQL client:

```
mysql --version
```


## Quick start (TL;DR)
1) Start MySQL locally with user `root` and empty password.
2) Create the `project_data` database (auto-create is enabled too).
3) Import the sample data from `mysqlsampledatabase.sql` into `project_data`.
4) Build and run the API.

Commands (macOS/Linux):

```
# 1) Start MySQL (examples below) and ensure it listens on 127.0.0.1:3306

# 2) Create DB (safe if it already exists)
mysql -u root -p'' -h 127.0.0.1 -P 3306 -e "CREATE DATABASE IF NOT EXISTS project_data CHARACTER SET utf8mb4;"

# 3) Import data (from the repo root)
mysql -u root -p'' -h 127.0.0.1 -P 3306 project_data < mysqlsampledatabase.sql

# 4) Build and run
./gradlew clean build
./gradlew bootRun
```

On Windows (PowerShell):

```
mysql -u root -p"" -h 127.0.0.1 -P 3306 -e "CREATE DATABASE IF NOT EXISTS project_data CHARACTER SET utf8mb4;"
mysql -u root -p"" -h 127.0.0.1 -P 3306 project_data < mysqlsampledatabase.sql

./gradlew.bat clean build
./gradlew.bat bootRun
```


## Starting MySQL with root and empty password
Choose one of the methods below.

- Native install (e.g., Homebrew on macOS):
  - Start the service (command varies by install). For Homebrew:
    - Start: `brew services start mysql`
    - Stop: `brew services stop mysql`
  - Ensure the `root` user has an empty password or set it temporarily:
    - If prompted for a password and you don’t know it, you can reset root in MySQL docs or set an empty one for local dev.

- Docker one-liner (ephemeral dev instance):

```
docker run --name mysql-lugnuts -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_ROOT_HOST=% -d mysql:8.4
```

After the container starts, use the import commands shown earlier.

Notes:
- The app connects to `127.0.0.1:3306` by default. If you change host/port, see "Configuration" below.
- Using an empty password is only recommended for local development.


## Configuration
The active profile is `mysql` (see `src/main/resources/application.properties`). The MySQL datasource defaults are in `src/main/resources/application-mysql.properties`:

- URL: `jdbc:mysql://${MYSQL_HOST:127.0.0.1}:${MYSQL_PORT:3306}/${MYSQL_DB:project_data}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true`
- User: `${MYSQL_USER:root}`
- Password: `${MYSQL_PASSWORD:}` (empty by default)
- JPA: `spring.jpa.hibernate.ddl-auto=none` (schema managed by SQL import)

You can override via environment variables, for example:

```
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
export MYSQL_DB=project_data
export MYSQL_USER=root
export MYSQL_PASSWORD=
```

If you prefer Hibernate to adjust schema automatically during development, set:

```
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```


## Build and Run
- Build:

```
./gradlew clean build
```

- Run (profile is already active):

```
./gradlew bootRun
```

- Change port (optional):

```
./gradlew bootRun --args='--server.port=9090'
```

When the app starts, visit:
- API docs/HAL Explorer: `http://localhost:8080/`
- Lugnuts base path: `http://localhost:8080/api/lugnuts`


## Importing the sample database
The repository includes `mysqlsampledatabase.sql` at the project root. Import it into the `project_data` database:

```
mysql -u root -p'' -h 127.0.0.1 -P 3306 project_data < mysqlsampledatabase.sql
```

If the file is large and you see "max_allowed_packet" errors, increase it in MySQL or import via a GUI client.


## Endpoints (Lugnuts)
Base: `http://localhost:8080/api/lugnuts`

- Customers
  - GET `/customers`
  - GET `/customers/{id}`
  - POST `/customers`
  - PUT `/customers/{id}`
  - DELETE `/customers/{id}`
- Products
  - GET `/products`
  - GET `/products/{code}`
  - POST `/products`
  - PUT `/products/{code}`
  - DELETE `/products/{code}`
- Orders
  - GET `/orders`
  - GET `/orders/{orderNumber}`
  - GET `/orders/{orderNumber}/details`
  - POST `/orders`
  - PUT `/orders/{orderNumber}`
  - DELETE `/orders/{orderNumber}`

Examples:

```
curl http://localhost:8080/api/lugnuts/customers
curl "http://localhost:8080/api/lugnuts/products?q=car"
```


## Troubleshooting
- Access denied for user 'root'@'localhost':
  - Ensure MySQL allows empty root password (Docker example uses `MYSQL_ALLOW_EMPTY_PASSWORD=yes`).
  - If your root has a password, set it via `export MYSQL_PASSWORD=yourPassword` or update `application-mysql.properties`.

- Unknown database 'project_data':
  - Create it and import the SQL as shown above. Auto-create is enabled, but it won’t populate tables without your import.

- Communications link failure:
  - Verify MySQL is running and listening on 127.0.0.1:3306.
  - If using Docker Desktop on Windows, confirm the port mapping is not blocked by another service.

- 404 for all Lugnuts endpoints:
  - Ensure you are running this app (`./gradlew bootRun`) and not a different main class.


## Project structure
- Main class: `com.example.lugnutsautomotive.LugnutsAutomotiveApplication`
- Controllers:
  - `com.example.lugnutsautomotive.web.CustomerController`
  - `com.example.lugnutsautomotive.web.ProductController`
  - `com.example.lugnutsautomotive.web.OrderController`
- Entities: `Customer`, `Product`, `ProductLine`, `CustomerOrder`, `OrderDetail`, ...
- Repositories: Spring Data JPA interfaces for querying the entities.
