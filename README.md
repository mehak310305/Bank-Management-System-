# 🏦 Banking Application (Backend - Spring Boot)

A scalable and secure backend banking application developed using Spring Boot and Java, implementing real-world financial workflows
including user authentication, account management, and transaction processing via RESTful APIs, enhanced with JWT-based security 
and clean architecture principles.

## 🚀 Features

- 🔐 User Registration & Login (JWT Authentication)
- 👤 Profile Management (Name, Email, Account Number,Balance)
- 💰 Account Balance Dashboard
- 💸 Deposit Money
- 💳 Withdraw Money
- 🔁 Transfer Money to another account
- 📊 Latest Transactions (Last 3 records)
- 📄 Transaction History
- 📥 PDF Download of Transactions
- 🔒 Secure APIs using Spring Security

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Hibernate / JPA
- MySQL
- Maven

---

## 📌 API Endpoints

### 🔐 Authentication

- POST `/api/users/register` → Register new user  
- POST `/api/auth/login` → Login & get JWT token  

---

### 👤 User

- GET `/api/users/profile` → Get user profile  
- PUT `/api/users/change_password` → Change user password  
---

### 📊 Dashboard

- GET `/api/users/dashboard` → Get current balance + latest transactions  

---

### 💸 Transactions

- POST `/api/transactions/deposit` → Deposit money  
- POST `/api/transactions/withdraw` → Withdraw money  
- POST `/api/transactions/transfer` → Transfer money  

---

### 📄 Reports

- GET `/api/transactions/transactions` → Get transaction history  
- GET `/api/users/download/statement` → Download transactions PDF  

---

## 🔐 Security

- JWT-based Authentication
- Token must be passed in header:
