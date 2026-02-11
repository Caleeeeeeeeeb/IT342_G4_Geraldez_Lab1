

🟢 Mini_app

A simple full-stack app with login, registration, dashboard, profile, and logout.

🚀 Tech Stack

Frontend: React.js

Backend: Spring Boot

Mobile: Android Studio (Iguana 2023.2.1)

Database: MySQL

🗄 Database Setup

Create the database:

CREATE DATABASE mini_app;



Configure backend (application.properties):

spring.datasource.url=jdbc:mysql://localhost:3306/mini_app
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
server.port=8080

⚙ Environment Variables

Frontend (.env):

REACT_APP_API_BASE_URL=http://localhost:8080/api


Mobile: Set API base URL in Retrofit/network config.

🖥 Running the Project
Backend
cd backend
mvn clean install
mvn spring-boot:run

Web Frontend
cd frontend
npm install
npm run dev


Runs at: http://localhost:3000

Mobile App

Open in Android Studio

Connect device/emulator

Run ‘app’

📌 API Endpoints
Endpoint	Method	Description
/api/auth/register	POST	Register user
/api/auth/login	POST	Login user
/api/user/profile	GET	Get profile
/api/user/profile	PUT	Update profile
/api/auth/logout	POST	Logout user
🎯 Features

✅ User Registration & Login

✅ Dashboard with user info

✅ Profile view & edit

✅ Logout functionality