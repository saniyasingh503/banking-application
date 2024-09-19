# Banking Application - Microservices Architecture

## Overview
This banking application project designed using a microservices architecture. It includes services for managing customers and accounts, as well as additional components for service registration and centralized configuration management.

## Microservices

### 1. Customer Management Service
This service provides the following APIs for managing customers:
- **Add Customer**: Create a new customer in the system.
- **Get All Customers**: Retrieve a list of all customers.
- **Get Single Customer Details**: Fetch details for a specific customer.
- **Update Customer Details**: Modify existing customer information.
- **Delete Customer**: Remove a customer from the system. This action also deletes the corresponding account from the Account Management Service.

### 2. Account Management Service
This service offers the following APIs for handling bank accounts:
- **Add Money to Account**: Add funds to a customer's account after validating their details.
- **Withdraw Money from Account**: Withdraw funds from a customer's account after validating their details.
- **Get Account Details**: Retrieve account information along with associated customer details.
- **Delete Account**: Remove an account from the system.

## Additional Components
To support the microservices, the following components have been implemented:

1. **API Gateway**: Serves as a single entry point for all microservices, routing requests to the appropriate service.
2. **Eureka Server**: Acts as a service registry, allowing microservices to discover each other.
3. **Centralized Configuration Management**: Manages configuration settings across all services for consistency and ease of updates.

## Getting Started

### Prerequisites
- Java JDK (version 11 or higher)
- Spring Boot
- Maven or Gradle (for dependency management)
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Running the Application
1. **Clone the Repository**
   ```bash
   git clone https://github.com/saniyasingh503/banking-application.git
   cd banking-application

#### Start the Services in Order
To ensure that all microservices function correctly, start the services in the following order:
1. **Eureka Server**: Start the Eureka server first to register all microservices.
2. **Config Server**: Start the Config server for centralized configuration.
3. **API Gateway**: Start the API gateway as the entry point.
4. **Customer Management Service**: Start the customer management service.
5. **Account Management Service**: Start the account management service.

### Accessing the Services
- **Eureka Server**: [http://localhost:8761/](http://localhost:8761/)
- **API Gateway**: [http://localhost:8083/](http://localhost:8083/)
- Access customer and account services through the API Gateway.

## Conclusion
This banking application provides a robust microservices architecture, with clear separation of concerns for managing customers and accounts. With exception handling and centralized configuration in place, the application is scalable and maintainable.
