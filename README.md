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

## Authentication

### Register User:

```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/register' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "test",
  "firstName": "User",
  "lastName": " Test",
  "email": "email@example.com",
  "password": "admin",
  "roles": [
    "ADMIN"
  ],
  "userId": "7"
}'
```

### Authenticate User:

```shell
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "test",
  "password": "admin"
}'
```

Response:

```shell
{
  "roles": [
    "ADMIN"
  ],
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6InRlc3QiLCJpYXQiOjE3NDc1NjE1MDYsImV4cCI6MTc0NzY0NzkwNn0.VzTEfqkvPwo59p9On4-ayTL3f8GSX19WYUCv32J4CnSkujyI3clW6dTBrlgfVdlO-vTdSUWxkpYyzPYvcgIx6Q",
  "username": "test"
}
```

### Access API with the token:

Api:

```shell
curl -H "Authorization: Bearer <TOKEN>" <API_URL>
```

Example:

```shell
curl -H "Authorization: Bearer \
eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6InplbmNrZSIsImlhdCI6MTc0NzU2MTAwMSwiZXhwIjoxNzQ3NjQ3NDAxfQ.\
suFSFmao2Bi8ve-wKkJBoNfWUmTp5Z2riTGhuNsPoO8plrQcnnrQW8eVF8wXbAAIwwrmBWgsC8j8WyX4rSWU8Q" \
http://localhost:8080/api/v1/product
```