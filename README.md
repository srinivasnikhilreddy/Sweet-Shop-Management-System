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

---

## **🔗 API Endpoints**
### Authentication

**POST /api/auth/register** ***(ADMIN)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/f5278137-3d26-4581-acff-b841a983b557" />
***(USER)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/d78306b6-918d-4719-b300-c6a92345578f" />
***(users database table)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/4cdc3609-6a15-4f50-a866-457173faa5da" />
***(admins database table)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/4886a249-b127-4cbe-9fbe-7b6a81f22caf" />

**POST /api/auth/login**
***(admin login)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/69d979c2-2173-4775-9056-37c83fc9d41b" />


### Sweets (Protected)

**POST   /api/sweets**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/cf2fc38a-7b5a-4fba-beed-4ee91272ab5c" />

**GET    /api/sweets**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/186393f8-dc01-4208-a5ad-ed25097bfa24" />

**GET    /api/sweets/search**
<img width="1366" height="768" alt="Image" src="https://github.com/user-attachments/assets/ff7c0845-de7d-49af-92be-879d19cedbe9" />

**PUT    /api/sweets/{id}**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/f2b8e529-a3c9-4463-a884-60a670848cdf" />

**DELETE /api/sweets/{id}   (Admin only)**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/a925c8b9-ad04-4e7e-bd8b-cd0fb0e14d1f" />

