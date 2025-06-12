# 💳 Banking Web Application

A secure and user-friendly online banking system built with Java Servlets, JSP, and MySQL. This application simulates essential banking operations such as deposits, withdrawals, transaction tracking, and credit card authorization for both users and administrators.

---

## 🚀 Features

### 👤 User Features
- 🔐 Secure login/logout system
- 🧾 View account details (name, balance, account number)
- 💵 Deposit & Withdraw money
- 📜 View transaction history within a date range
- 💳 Authorize credit card transactions (amount < ₹1,00,000)

### 🛠️ Admin Features
- 🔐 Admin authentication
- ➕ Credit and ➖ debit from any user's account
- 📋 View all users and account summaries
- 📈 View all user transactions

---

## 🧰 Tech Stack

- **Frontend:** HTML, CSS, JSP
- **Backend:** Java Servlets
- **Database:** MySQL
- **Server:** Apache Tomcat
- **IDE:** IntelliJ IDEA / Eclipse

---

## 🗃️ Database Schema

### Tables
- `users` – Stores login credentials and roles
- `accounts` – Stores account balances and holder info
- `transactions` – Logs every transaction
- `credit_card` – Stores credit card info for authorization

---

## 📂 Project Structure

Bank_App/
├── src/
│ └── co.bank.web/
│ ├── LoginServlet.java
│ ├── DepositWithdrawServlet.java
│ ├── AdminServlet.java
│ └── TransactionHistoryServlet.java
├── WebContent/
│ ├── login.jsp
│ ├── dashboard.jsp
│ ├── depositWithdraw.jsp
│ ├── admin.jsp
│ └── transactionHistory.jsp
└── WEB-INF/
└── web.xml


---

## 🏁 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Apache Tomcat 9 or higher
- MySQL Server
- Git

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/mahendramahato/Bank_App.git
   cd Bank_App
