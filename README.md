# JOIN TEST CASE BACKEN
Project built for JOIN in the selection process

## Local Database connection configuration

| Property                | Value                   |
|-------------------------|-------------------------|
| Address                 | <http://localhost:5432> |
| User                    | join                    |
| Password                | joinpwd                 |
| Database                | join                    |
| Schema                  | joindsv                 |
|                         |                         |

## Pre-requisites for running the application

1. [Docker and docker compose](https://docs.docker.com/get-docker/)
2. Java 21
3. Maven

## Step-by-step backend installation
1. Run command ```docker-compose up -d``` inside the project folder.
2. Run command ```mvn clean install``` inside the project folder.
3. If build success. Run command ```mvn spring-boot:run``` to up spring boot.
4. That's it, the application is running.

## Percentage of code coverage up to version 1.0 (07/11/2024)

### |██████████████░░░░░░░░░| 85%

## List of version 1.0 endpoints

| HTTP Verb | Context            | Description             |
|-----------|--------------------|-------------------------|
| POST      | /join/login        | Authenticate in server  |
|           |                    |                         |
| GET       | /join/category     | List all categories     |
| POST      | /join/category/:id | Find category by id     |
| PUT       | /join/category/:id | Update data in category |
| DELETE    | /join/category/:id | Delete some category    |
|           |                    |                         |
| GET       | /join/product      | List all products       |
| POST      | /join/product      | Create new product      |
| PUT       | /join/product/:id  | Update data in product  |
| GET       | /join/product/:id  | Find product by id      |
| DELETE    | /join/product/:id  | Delete some product     |
|           |                    |                         |

## Postman collection

![POSTMAN image](https://miro.medium.com/v2/resize:fit:720/format:webp/1*UjfpcPx0p410o13vpB7mlQ.png)
- [Collection](./JOIN%20TESTCASE.postman_collection.json)
- [Environment variables](./Join%20TestCase.postman_environment.json)