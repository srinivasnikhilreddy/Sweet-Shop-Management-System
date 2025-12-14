# 🍬 Sweet Shop Management System

    [![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
    [![Frontend](https://img.shields.io/badge/Frontend-Angular-blue)](#)
    [![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-orange)](#)
    [![Database](https://img.shields.io/badge/Database-MSSQL-lightgrey)](#)

##   **TDD Kata – Full Stack Application**

## **📌 Project Overview**

The **Sweet Shop Management System** is a full-stack web application designed and implemented as part of a **Test-Driven Development (TDD) Kata.**
The system enables users to browse, search, and purchase sweets, while administrators can manage sweets and inventory securely.

**This project demonstrates:**
- RESTful API design
- Secure authentication & authorization
- Database-backed persistence
- Modern frontend development
- Test-driven backend development
- Clean coding practices
- Responsible and transparent AI usage in development

---

## **🛠 Tech Stack**
### Backend 
- Java – Spring Boot
- Spring Security (JWT Authentication)
- JPA / Hibernate
- Relational Database (MSSQL)
- JUnit & Mockito for testing

### Frontend 
- Angular
- TypeScript
- HTML5 / CSS3
- Angular Forms & HttpClient

### Tooling 
- Git & GitHub
- Maven
- Node.js & npm

---

## **🔐 Features**
### User Authentication
- User registration
- User login
- JWT-based authentication
- Role-based access (Admin / User)

### Sweet Management (Protected)
- Add a new sweet (Admin)
- View all sweets
- Search sweets by:
  -- Name
  -- Category
  -- Price range
- Update sweet details (Admin)
- Delete sweet (Admin)

### Inventory Management (Protected)
- Purchase a sweet (decreases quantity)
- Restock a sweet (Admin only)
- Purchase button disabled when stock is zero

### Frontend UI
- Modern, responsive UI
- Dashboard listing all sweets
- Search & filter functionality
- Admin-only forms for Add / Edit / Delete
- Image upload support for sweets

