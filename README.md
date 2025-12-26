# Patient Management Microservices

Production-style microservices project built with Java and Spring Boot.

## Tech Stack
- Java
- Spring Boot
- PostgreSQL
- Docker
- gRPC
- Kafka

## Current Status
- Patient Service (CRUD, Validation, DB)
- Billing Service (gRPC Server)
- gRPC communication implemented
- Event-driven communication using Kafka (Producer)
- Analytics Service (Kafka Consumer, Dockerized)

## Services
- Patient Service (CRUD, DB, Validation)
- Billing Service (gRPC Server)
- Analytics Service (Kafka Consumer)
- API Gateway (Routing and request forwarding)

## Architecture Overview
Clients communicate with backend services through an API Gateway.  
The gateway routes requests to internal microservices and acts as a single entry point.

## Project Status
🚧 In Progress