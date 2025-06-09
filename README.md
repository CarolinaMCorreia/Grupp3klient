# 🐾 Pet Registry — Fullstack Java Application

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Database Setup](#database-setup)
- [CI/CD Deployment](#cicd-deployment)
- [Testing](#testing)
- [Group Members](#group-members)

---

## 📘 Overview

**Pet Registry** is a fullstack Java application that provides a complete system for registering and managing pets and their owners.

The system is built with a **Spring Boot backend REST API** and a **JavaFX frontend GUI** that communicates with the backend. This project is designed to simulate real-world use for animal clinics, pet clubs, or individuals who manage many animals.

---

## 🏗️ Architecture

```
JavaFX GUI Client ↔ REST API (Spring Boot) ↔ MySQL Database (AWS RDS)
                                 │
                                 └── Deployed on AWS Elastic Beanstalk via CI/CD
```

---

## ✨ Features

- ✅ Register, update, delete, and list pets and owners
- 🔐 User authentication system (admin login required for user operations)
- 🖥️ JavaFX client GUI (FXML-based layout)
- 📦 Spring Boot backend REST API
- 🐬 MySQL database (cloud-hosted via AWS RDS)
- 🔁 CI/CD automated deployment with AWS CodeBuild + CodePipeline
- 📚 Swagger UI for live API testing
- ✅ JUnit testing for backend logic

---

## 💻 Technologies

- **Spring Boot** — RESTful backend
- **JavaFX** — Desktop GUI with `.fxml` files
- **MySQL** — Relational database (hosted on AWS RDS)
- **Swagger** — Interactive API docs
- **JUnit** — Unit testing
- **AWS Elastic Beanstalk** — Deployment environment
- **AWS CodePipeline & CodeBuild** — CI/CD
- **Maven** — Build and dependency management

---

## 🛠️ Installation

### Clone the repository

```bash
git clone https://github.com/CarolinaMCorreia/grupp3Molnet.git
```

---

### Run the backend (Spring Boot API)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

---

### Run the frontend (JavaFX GUI)

Make sure you are using Java 17+ and a JavaFX-compatible IDE like IntelliJ or Eclipse with JavaFX SDK.

```bash
cd javafx-client
# Run the Main class (likely named Main.java)
```

If `.fxml` files are used, make sure they are located in the `resources/` folder and loaded via `FXMLLoader`.

---

## 📑 API Documentation

When the Spring Boot application is running, visit:

- **Local Swagger UI**:  
  `http://localhost:5000/swagger-ui/index.html`

- **Deployed Swagger UI** (AWS Elastic Beanstalk):  
  `http://husdjursregister1-env.eba-gzkbcjgw.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html`

Example endpoints:
- `GET /api/pet/all` — get all pets  
- `POST /auth/signup` — register new user  
- `POST /auth/login` — login and receive JWT  
- `PUT /api/users/id/{id}` — update user info (admin required)

---

## 🐬 Database Setup

This project uses a MySQL database hosted on AWS RDS.

### Manual setup for first run:

1. Log in to your MySQL instance
2. Create the database:

```sql
CREATE DATABASE husdjursregister;
USE husdjursregister;
```

On app startup, Hibernate will auto-generate all tables.

An admin user is generated automatically with:

```json
{
  "username": "admin",
  "password": "adminpassword"
}
```

You need to log in as admin to perform operations on users.

---

## 🚀 CI/CD Deployment

This project uses AWS services for automated build and deployment.

- ✅ **CodeBuild** compiles and runs tests on each push to `main`
- ✅ **CodePipeline** deploys to **AWS Elastic Beanstalk** automatically after a successful build
- ✅ **Elastic Beanstalk** hosts and scales the Spring Boot app
- ✅ **AWS RDS** hosts the database in the cloud

---

## ✅ Testing

The backend is tested with **JUnit** to ensure business logic functions as expected.

To run tests:

```bash
mvn test
```

Tests are also triggered in the CI/CD pipeline to prevent bugs from reaching production.

---

## 👥 Group Members

- Carolina Correia  
- Joakim Bagge  
- Pontus Kävrestad  
- Louise Siesing

---

## 📬 Contact

Questions or feedback? Feel free to [open an issue](https://github.com/CarolinaMCorreia/grupp3Molnet/issues) on the repository.

