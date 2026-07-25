# OrthoCare — Orthopedic Hospital Management System

Java web application built with Jakarta Servlets, JSP/JSTL, JDBC, MySQL and Apache Tomcat 10+.

## Stack
- **Language:** Java 17
- **Web:** Jakarta Servlets 6.0 + JSP + JSTL 3.0
- **Persistence:** JDBC + MySQL 8
- **Server:** Apache Tomcat 10.1+ (Jakarta EE 10)
- **Build:** Maven

## Architecture (MVC)
```
src/main/java/com/orthocare/
├── controller/   # Jakarta HttpServlets (routing / request handling)
├── service/      # Business logic
├── dao/          # Data access (raw SQL via JDBC)
├── model/        # POJOs mirroring DB tables
├── util/         # DBUtil, PasswordUtil, ValidationUtil, CodeGenerator
└── filter/       # AuthFilter (session + role guard)
src/main/webapp/
├── WEB-INF/
│   ├── web.xml
│   └── views/    # JSP pages (protected from direct access)
└── css/
```

## Setup

### 1. Create the database
```bash
mysql -u root -p < sql/schema.sql
```
This creates the `orthocare` database, all tables, and seeds an admin user:
- **Username:** `admin`
- **Password:** `admin123` (plain-text)

### 2. Configure DB connection
Edit `src/main/resources/db.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/orthocare?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8
db.user=root
db.password=your_password
```

### 3. Build the WAR
```bash
mvn clean package
```
Output: `target/orthocare.war`

### 4. Run the application
You can run it in two ways:

#### Option A: Running from command line (Cargo plugin)
Execute:
```bash
mvn cargo:run
```
This automatically downloads, configures, and starts Tomcat 9 on port `8080`.
Access the app at: `http://localhost:8080/orthocare/`

#### Option B: Deploy to Tomcat 9 in IntelliJ IDEA
1. Open the project in IntelliJ IDEA as a Maven project.
2. Configure a local **Tomcat 9** Run/Debug configuration.
3. Deploy the `orthocare:war` or `orthocare:war exploded` artifact.
4. Access the app at: `http://localhost:8080/orthocare/`

## Features
1. **Auth & Users** — roles: ADMIN, DOCTOR, RECEPTIONIST, PATIENT. Plain-text passwords, HttpSession tracking.
2. **Patient Management** — CRUD, auto patient code, allergies/diseases/insurance.
3. **Doctor Management** — profile linked to user, specialization, fee, availability.
4. **Appointments** — auto appointment code, statuses PENDING/CONFIRMED/COMPLETED/CANCELLED.
5. **Medical Records** — clinical notes per patient.
6. **Prescriptions** — header + multiple items (medicine, morning/afternoon/night, duration).
7. **Billing & Payments** — GST 18%, discount, net amount, multiple payments per bill.
8. **Dashboard** — totals for patients, doctors, appointments, today's appointments, bills, revenue.
