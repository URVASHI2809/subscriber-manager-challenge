# Subscriber Manager

A small Spring Boot service for managing telecom subscribers — the starter project for the technical interview.

- Group id: `com.interview`, artifact id: `subscriber-manager`
- Spring Web + Spring Data JPA + embedded H2 (MySQL-compatibility mode), Spring Boot 3.3.5, Java 17+
- Entity `Subscriber` (`id`, `msisdn`, `planCode`, `activatedOn`) with `GenerationType.IDENTITY`
- `SubscriberRepository` extends `CrudRepository<Subscriber, Integer>`
- `SubscriberService` with `getAllSubscribers`, `getSubscriberById`, `saveSubscriber`, `deleteSubscriber`
- `SubscriberController` REST endpoints under `/subscribers` returning `ResponseEntity`
  (GET all → OK, GET by id → OK / NOT_FOUND, POST → CREATED, PUT → OK / NOT_FOUND, DELETE → NO_CONTENT)
- Unit tests: `SubscriberRepositoryTest` (embedded H2), `SubscriberControllerTest` (Mockito)

**Domain note:** `msisdn` is the subscriber's phone number in international format (e.g. `34600111222`) — in a real network it uniquely identifies a subscriber. `planCode` is the tariff plan (e.g. `PRE100`, `POST500`), and `activatedOn` is the service activation date.

> **Note on the database for this session:** this copy runs against an embedded, in-memory H2
> database configured in MySQL-compatibility mode instead of a real MySQL server — no install,
> no Docker, nothing to start. It's a same-session accommodation, not a change to the exercise
> itself: the entity, repository, and API all behave the same way. `spring.jpa.hibernate.ddl-auto=update`
> still creates the schema automatically on first run, exactly as it would against MySQL.

## Prerequisites

- JDK 17+
- Maven 3.6+
- (No database install needed — H2 runs embedded in the JVM.)

## Setup

Nothing to do — the datasource is already configured in `src/main/resources/application.properties`
to point at an in-memory H2 instance. Just build and run.

## Run the tests

```bash
mvn test
```

`SubscriberRepositoryTest` runs against Spring Boot's auto-configured embedded test database (H2),
so behavior is the same as it would be against real MySQL — just without anything to install or
start first. Because the database is in-memory, it's re-created fresh each run, so there's no
persistent instance to inspect afterward with a SQL client the way there would be with MySQL.

`SubscriberControllerTest` is a pure Mockito test and needs no database either way.

## Run the application

```bash
mvn spring-boot:run
```

Then exercise it:

- `GET    http://localhost:8080/subscribers/`
- `GET    http://localhost:8080/subscribers/1`
- `POST   http://localhost:8080/subscribers/`  body: `{"msisdn":"34600555666","planCode":"PRE100","activatedOn":"2024-03-01"}`
- `PUT    http://localhost:8080/subscribers/1` body: `{"msisdn":"34600555666","planCode":"POST500","activatedOn":"2024-03-01"}`
- `DELETE http://localhost:8080/subscribers/2`
