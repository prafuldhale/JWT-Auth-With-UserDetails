# User Service Application

## Overview
The **User Service Application** is a Spring Boot-based project designed to manage user-related operations. It provides RESTful APIs for creating, retrieving, updating, and deleting user details, along with managing associated claims and nominees.

## Features
- User registration and authentication with JWT.
- CRUD operations for user details.
- Management of user claims and nominees.
- Secure endpoints with Spring Security.
- Integration with a relational database using JPA and MySQL.

## Technologies Used
- **Java**: Programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: For securing the application.
- **JPA/Hibernate**: For database interaction.
- **Maven**: Build and dependency management tool.
- **MySQL**: Database for storing user data.

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- MySQL Server installed and running
- An IDE like IntelliJ IDEA or Eclipse
- Postman or any API testing tool (optional)

## Getting Started

### Configure MySQL Database
1. Create a database in MySQL:
   ```sql
   CREATE DATABASE user_service;