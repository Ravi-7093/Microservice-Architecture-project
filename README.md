# Microservices Architecture Project

This project implements a robust microservices architecture with Spring Boot, featuring:

- **Product Service**: Manages product catalog, utilizing MongoDB for data persistence.
- **Order Service**: Handles order processing, interfacing with a MySQL database and checking inventory levels.
- **Inventory Service**: Tracks stock levels to ensure order fulfillability.
- **Notification Service**: Stateless service that dispatches notifications to users upon successful order placement.
- **Communication**: Synchronous for user notifications and asynchronous for service interactions using message brokers like RabbitMQ and Kafka.
- **Service Discovery**: Utilizes Eureka for locating service instances.
- **Centralized Configuration**: Manages settings across services with a Config Server.
- **Distributed Tracing**: Integrates with Zipkin for tracing requests across the architecture.
- **Event-Driven Architecture**: Adopts an event-driven model to handle operations.
- **Centralized Logging**: Aggregates logs for monitoring using Elasticsearch, Logstash, and Kibana.
- **Circuit Breaker**: Implements resilience patterns with Resilience4j.
- **Security**: Secures microservices using Keycloak for authentication and authorization.
- **API Gateway**: Central entry point for client requests, providing a layer of abstraction over service endpoints.
