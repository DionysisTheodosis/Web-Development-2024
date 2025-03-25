# 🏥 MedCare - Doctor Appointment Platform

MedCare is a full-stack web application designed to facilitate doctor appointments efficiently. This platform allows patients to book appointments, doctors to manage schedules, and admins to oversee the system. The project follows a **RESTful API architecture** and uses **Docker** for containerized deployment.

---

## 📌 Features
✅ User authentication (Patients, Doctors, Secretaries)  
✅ Appointment scheduling system  
✅ Role-based access control  
✅ Secure backend with session-based authentication  
✅ Fully responsive UI  

---

## 🚀 Getting Started

### **Prerequisites**
Before running the project, ensure you have **Docker** installed on your system.  
🔗 [Download Docker](https://www.docker.com/get-started)  

---

## 🏗️ **Run the Application with Docker**
Follow these steps to start the application:  

1️⃣ **Clone the repository:**  
```sh
git clone https://github.com/yourusername/medcare.git
cd medcare
```

2️⃣ **Build & Run with Docker:**  
```sh
docker-compose up --build
```

3️⃣ **Access the application:**  
- Frontend: [http://localhost:80](http://localhost:80)  
- Backend API: [http://localhost:8080](http://localhost:8080)  

---

## ⚠️ **Port Restrictions**
Before running the app, make sure **none of the following ports** are in use:  
🚫 `80` → Frontend  
🚫 `8080` → Backend API  
🚫 `6379` → Redis Database 
🚫 `3306` → MySql Database   

To check if a port is in use:  
```sh
netstat -anp | grep :3306
```
If needed, stop any conflicting process:  
```sh
kill -9 <PID>
```

---

## 🛑 **Stopping the Application**
To stop all running containers:  
```sh
docker-compose down
```

To stop and **remove all containers, networks, and volumes**:  
```sh
docker-compose down --volumes
```

---

## 🐜 **License**
This project is licensed under the **MIT License**.  

---

