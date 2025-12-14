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

**POST /api/auth/register**
***(ADMIN)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/f5278137-3d26-4581-acff-b841a983b557" />

***(USER)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/d78306b6-918d-4719-b300-c6a92345578f" />

**POST /api/auth/login**
***(admin login)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/69d979c2-2173-4775-9056-37c83fc9d41b" />


### Sweets (Protected)

**POST   /api/sweets**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/cf2fc38a-7b5a-4fba-beed-4ee91272ab5c" />

**GET    /api/sweets**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/186393f8-dc01-4208-a5ad-ed25097bfa24" />

**GET    /api/sweets/search**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/ff7c0845-de7d-49af-92be-879d19cedbe9" />

**PUT    /api/sweets/{id}**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/f2b8e529-a3c9-4463-a884-60a670848cdf" />

**DELETE /api/sweets/{id}   (Admin only)**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/a925c8b9-ad04-4e7e-bd8b-cd0fb0e14d1f" />


### Inventory (Protected)
**POST /api/sweets/{id}/purchase**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/67a12200-0eb5-4fbe-ad89-99b7160977a5" />

**POST /api/sweets/{id}/restock   (Admin only)**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/5efa17dc-8656-44a3-a32a-ebe6719bbfbd" />

---

## **🧪 Test-Driven Development (TDD)**

This project follows Test-Driven Development principles, especially on the backend:
- Tests were written before implementing business logic
- Followed Red → Green → Refactor workflow
- Unit tests cover:
  - Service layer logic
  - Inventory edge cases
  - Authentication logic
- High focus on meaningful test cases instead of superficial coverage
A test report is included as part of the deliverables.

**Tests Passed**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/af2ae013-975d-48d2-8ebe-98456c4aafab" />

---

## **🧼 Clean Coding Practices**

- Clear separation of concerns (Controller, Service, Repository)
- SOLID principles applied where appropriate
- Readable method and variable naming
- Proper error handling and validation
- Modular and maintainable Angular components

---

## **UI ScreeenShots**
***(Admin Registration)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/5001b7e0-0d79-44ac-bfb6-e6346425ec9e" />

***(Admin login)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/2cf27bc0-9e1e-47f5-846b-64230613354c" />

***(Admin Dashboards)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/c5b54c83-356d-43c7-8851-020880f14c07" />

<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/8bea4b86-fb8d-458f-98e3-07890bc2f692" />

***(Add Sweets By Admin Only)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/4d9a88f8-f9e8-4195-b4f6-c33c62077462" />

***(Update Sweets By Admin Only)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/e0263dfe-0eba-4c09-b830-96a65fc1ad77" />

***(User Registration)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/a156f769-20cb-48ee-b4b5-7971e1ed39ed" />

***(User Login)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/391800fc-4dbd-48af-bc3c-701488d3794a" />

***(User Dashboards)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/0a3e55a9-3af5-4b58-9b06-3bd700ca752b" />

<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/866d4a9d-fe69-4067-ad65-7df2335da995" />

***(Searching/Filtering By Both Admin and User)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/69466335-3a5b-4483-ac70-20f0c2158241" />

---

***(sweets database table)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/0c22dbfd-3224-4731-82e9-0cde966db0e7" />

***(users database table)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/4cdc3609-6a15-4f50-a866-457173faa5da" />

***(admins database table)***
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/4886a249-b127-4cbe-9fbe-7b6a81f22caf" />

---

## ***🤖 My AI Usage***
### AI Tools Used
- ChatGPT (OpenAI)
### How I Used AI
- To brainstorm REST API endpoint structure and request/response design
- To generate initial boilerplate for Angular components and Spring Boot controllers
- To assist in debugging TypeScript and Spring Boot errors
- To help refine UI design and CSS for a professional look
- To support writing and refining unit test scenarios

### Reflection
AI significantly improved development speed by reducing time spent on boilerplate code and syntax-level issues.
However, all architectural decisions, business logic, validations, testing strategy, and final implementations were designed, reviewed, and refined by me to ensure correctness, maintainability, and alignment with the kata requirements.
AI was used as a development assistant, not as a replacement for understanding or ownership of the code.

---

## **📂 Git & Version Control**

- Git was used throughout development
- Commits are frequent and descriptive
- Commits where AI assistance was used include AI co-authorship, as required
Example:
- Co-authored-by: ChatGPT <AI@users.noreply.github.c

---

## **📊 Test Report**

- Backend unit test results are included
- Tests executed using Maven
- Output/screenshots available in the repository

**Tests Passed**
<img width="964" height="474" alt="Image" src="https://github.com/user-attachments/assets/af2ae013-975d-48d2-8ebe-98456c4aafab" />
