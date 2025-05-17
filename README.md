# spring-demo

## Run Locally

* Build and run tests

```shell
./gradlew clean build
```

* Create Jar

```shell
./gradlew clean bootJar
```

* Start Spring Boot Application:

```shell
./gradlew bootRun
```

## Docker Setup

### Prerequisites

- Docker
- Docker Compose

### Build and Run with Docker

1. Build the application:

```shell
./gradlew clean build
```

2. Start the application and PostgreSQL database:

```shell
docker-compose up -d
```

3. Stop the application:

```shell
docker-compose down
```

To stop the application and remove the volumes:

```shell
docker-compose down -v
```

### Docker Configuration

The Docker setup includes:
- A PostgreSQL database container
- The Spring Boot application container

The PostgreSQL database is configured with:
- Database name: springdemo
- Username: postgres
- Password: postgres
- Port: 5432

## Open API Documentation

* [Access swagger api](http://localhost:8080/swagger-ui/index.html) and
  corresponding [openAPI docs here](http://localhost:8080/api-docs)

## Actuator Endpoints

http://localhost:8080/actuator
