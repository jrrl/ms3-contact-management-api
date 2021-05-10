# MS3 Contact API

Programming exercise for MS3, created using Spring Boot and Java.

API is current deployed as a container running on Google Cloud Run, with Apigee as the API Gateway.

Base URL: https://juanliban-eval-prod.apigee.net/v1/contacts

## Documentation

OpenAPI specification and postman collection are inside the spec folder. SQL statements for table creation are inside the database folder.

API documentation can be found [here](https://juanliban-eval-ms3.apigee.io/docs/ms3-contact-api/1/overview). Requests made on the documentation page will be run against the container deployed in Google Cloud Run.

#### Changes from problem set and assumptions:
- changed JSON response to be more uniform
- required at least one communication for a contact
- communication type cannot be changed once created
- deleting the contact will delete all addresses and communications

## Tech Stack
**Development**

- Java 11
- Spring Boot

**Build Tools**
- Gradle
- [Jib](https://github.com/GoogleContainerTools/jib) (for generating the container)
- Circle CI for CI/CD (https://app.circleci.com/pipelines/github/jrrl/ms3-contact-management-api?invite=true)

**Deployment**
- Google Cloud Run
- Google Cloud SQL
- Apigee

## Run Locally

#### Prerequisites:
- Docker Desktop
- Java 11
- a database client

#### Instructions:

Clone project

```bash
  git clone https://github.com/jrrl/ms3-contact-management-api
```

Go to the project directory

```bash
  cd ms3-contact-management-api
```

Run docker-compose to spin up db container:

```bash
  docker-compose up -d
```

Use any database client to connect to the database, and run the init_database.sql script inside the database folder on the db.
Default database details:
```bash
  url: jdbc:sqlserver://localhost:1433;database=tempdb
  username: sa
  password: Password123!
```

Run service

```bash
  gradle bootRun --args='--spring.profiles.active=dev'
```

To create a container image locally, run:

```bash
  gradle jibDockerBuild
```