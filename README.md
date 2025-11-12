# Oligarch Rating Service 💼
**Tech:** Java 17 · Spring Boot · JPA · H2 · (WireMock for API tests)

## Overview
Microservice for aggregating and exposing “oligarch” ratings via REST endpoints.
Includes integration tests and in-memory H2 for quick demos.

## Endpoints
- `GET /health` → `{ "status": "ok" }`
- (Planned) `GET /ratings/{id}` → fetch a rating
- (Planned) `POST /ratings` → create/update rating

## Quickstart
```bash
mvn test
mvn spring-boot:run
# open http://localhost:8080/health
