# 🖥️ Pet Registry — JavaFX Client

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Running the App](#running-the-app)
- [Project Structure](#project-structure)
- [API Integration](#api-integration)

---

## 📘 Overview

This is a **JavaFX-based desktop client** for the **Pet Registry API**.

It allows users to:
- Log in
- View, add, edit, and delete pets
- View and manage user data (admin-only)

The client communicates with a RESTful backend API (built in Spring Boot) via HTTP.

---

## ✨ Features

- JavaFX GUI built with `.fxml` views
- Integrated login with JWT authentication
- CRUD operations for pets and users
- Organized using MVC pattern
- Uses `HttpURLConnection` or a similar client to communicate with the API

---

## 💻 Technologies

- **Java 17+**
- **JavaFX 17+**
- **FXML** for view layout
- **SceneBuilder** (optional) for editing `.fxml` files
- **Maven** for build and dependency management

---

## 🛠️ Installation

1. Clone this repo (or the `client/` subfolder if part of a monorepo):
   ```bash
   git clone https://github.com/your-username/pet-registry-client.git
   ```

2. Open the project in **IntelliJ**, **Eclipse**, or another JavaFX-compatible IDE.

3. Make sure JavaFX SDK is properly configured in your project’s libraries.

---

## ▶️ Running the App

Locate the `Main.java` (or similarly named class) and run it.

```bash
mvn javafx:run
```

Or, if you're using an IDE, right-click the `Main` class and select **Run**.

---

## 🧭 Project Structure

```bash
src/
├── main/
│   ├── java/
│   │   └── controllers/        # JavaFX controller classes
│   │   └── models/             # DTOs / data models
│   │   └── Main.java           # Application entry point
│   └── resources/
│       └── views/              # .fxml layout files
│       └── application.css     # Optional styling
```

---

## 🌐 API Integration

The JavaFX client connects to the backend API hosted at:

```http
http://husdjursregister1-env.eba-gzkbcjgw.eu-north-1.elasticbeanstalk.com
```

Make sure the backend is:
- Deployed and running
- Accepting requests from this client (CORS allowed if needed)
- Matching the same endpoint structure as used in the client code

---

### Example API Usage (from client code)

- `POST /auth/login` — Log in and retrieve JWT  
- `GET /api/pet/all` — Fetch all pets  
- `POST /api/pet` — Add a new pet  
- Authenticated requests include JWT in `Authorization: Bearer <token>` headers

---

## 📝 Notes

- Admin credentials are typically:
  ```
  username: admin
  password: adminpassword
  ```
- You may need to adjust URLs or ports in the source code depending on your local or deployed API environment.

---

## 📬 Contact

For help or contributions, feel free to create an issue or pull request.

