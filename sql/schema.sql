-- OrthoCare schema
DROP DATABASE IF EXISTS orthocare;
CREATE DATABASE orthocare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE orthocare;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(128) NOT NULL,
    full_name VARCHAR(120) NOT NULL,
    email VARCHAR(120),
    phone VARCHAR(20),
    role ENUM('ADMIN','DOCTOR','RECEPTIONIST','PATIENT') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_code VARCHAR(30) UNIQUE NOT NULL,
    user_id INT NULL,
    full_name VARCHAR(120) NOT NULL,
    age INT,
    gender ENUM('MALE','FEMALE','OTHER'),
    dob DATE,
    blood_group VARCHAR(5),
    height_cm DECIMAL(6,2),
    weight_kg DECIMAL(6,2),
    phone VARCHAR(20),
    email VARCHAR(120),
    address VARCHAR(255),
    emergency_contact VARCHAR(20),
    allergies TEXT,
    existing_diseases TEXT,
    insurance_details TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    qualification VARCHAR(120),
    experience_years INT,
    specialization VARCHAR(120),
    consultation_fee DECIMAL(10,2),
    available_days VARCHAR(120),
    available_times VARCHAR(120),
    department ENUM('ORTHOPEDICS','PHYSIOTHERAPY') NOT NULL DEFAULT 'ORTHOPEDICS',
    contact VARCHAR(20),
    email VARCHAR(120),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_code VARCHAR(30) UNIQUE NOT NULL,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    department ENUM('ORTHOPEDICS','PHYSIOTHERAPY') NOT NULL,
    appointment_dt DATETIME NOT NULL,
    reason VARCHAR(255),
    status ENUM('PENDING','CONFIRMED','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

CREATE TABLE medical_records (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_date DATE NOT NULL,
    title VARCHAR(180) NOT NULL,
    description TEXT,
    created_by INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE medicines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    unit VARCHAR(30),
    description VARCHAR(255)
);

CREATE TABLE prescriptions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    prescribed_date DATE NOT NULL,
    notes TEXT,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);

CREATE TABLE prescription_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    prescription_id INT NOT NULL,
    medicine_id INT NOT NULL,
    dosage VARCHAR(60),
    morning BOOLEAN DEFAULT FALSE,
    afternoon BOOLEAN DEFAULT FALSE,
    night BOOLEAN DEFAULT FALSE,
    duration_days INT,
    instructions VARCHAR(255),
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);

CREATE TABLE bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bill_number VARCHAR(30) UNIQUE NOT NULL,
    patient_id INT NOT NULL,
    appointment_id INT,
    total_amount DECIMAL(12,2) NOT NULL,
    gst_amount DECIMAL(12,2) NOT NULL,
    discount DECIMAL(12,2) DEFAULT 0,
    net_amount DECIMAL(12,2) NOT NULL,
    status ENUM('UNPAID','PARTIAL','PAID') DEFAULT 'UNPAID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE SET NULL
);

CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bill_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    paid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    method ENUM('CASH','CARD','UPI') NOT NULL,
    reference_id VARCHAR(80),
    FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE
);

-- Seed admin (password = admin123 plaintext)
INSERT INTO users (id, username, password_hash, full_name, email, phone, role)
VALUES (1, 'admin', 'admin123', 'System Administrator', 'admin@orthocare.local', '9999999999', 'ADMIN');

-- Seed a few medicines
INSERT INTO medicines (id, name, unit, description) VALUES
(1, 'Paracetamol 500mg','tablet','Pain relief / fever'),
(2, 'Ibuprofen 400mg','tablet','Anti-inflammatory'),
(3, 'Calcium + Vitamin D3','tablet','Bone health'),
(4, 'Diclofenac Gel','tube','Topical anti-inflammatory');

-- Seed users (passwords are plain text)
-- Doctors
INSERT INTO users (id, username, password_hash, full_name, email, phone, role) VALUES
(2, 'dr_john', 'doctor123', 'Dr. John Doe', 'john.doe@orthocare.local', '9876543210', 'DOCTOR'),
(3, 'dr_sarah', 'doctor123', 'Dr. Sarah Smith', 'sarah.smith@orthocare.local', '9876543211', 'DOCTOR');

-- Patients
INSERT INTO users (id, username, password_hash, full_name, email, phone, role) VALUES
(4, 'bruce', 'patient123', 'Bruce Wayne', 'bruce.wayne@waynecorp.com', '9876543212', 'PATIENT'),
(5, 'clark', 'patient123', 'Clark Kent', 'clark.kent@dailyplanet.com', '9876543213', 'PATIENT'),
(6, 'diana', 'patient123', 'Diana Prince', 'diana.prince@museum.org', '9876543214', 'PATIENT');

-- Receptionist
INSERT INTO users (id, username, password_hash, full_name, email, phone, role) VALUES
(7, 'receptionist', 'receptionist123', 'Alice Johnson', 'alice.j@orthocare.local', '9876543215', 'RECEPTIONIST');

-- Seed Doctors details
INSERT INTO doctors (id, user_id, qualification, experience_years, specialization, consultation_fee, available_days, available_times, department, contact, email) VALUES
(1, 2, 'MBBS, MS (Orthopedics)', 12, 'Joint Replacement & Arthroscopy', 500.00, 'Monday, Wednesday, Friday', '10:00 AM - 01:00 PM', 'ORTHOPEDICS', '9876543210', 'john.doe@orthocare.local'),
(2, 3, 'MBBS, DNB (Physiotherapy)', 8, 'Sports Rehabilitation', 400.00, 'Tuesday, Thursday, Saturday', '02:00 PM - 05:00 PM', 'PHYSIOTHERAPY', '9876543211', 'sarah.smith@orthocare.local');

-- Seed Patients details
INSERT INTO patients (id, patient_code, user_id, full_name, age, gender, dob, blood_group, height_cm, weight_kg, phone, email, address, emergency_contact, allergies, existing_diseases, insurance_details) VALUES
(1, 'PAT-10001', 4, 'Bruce Wayne', 38, 'MALE', '1988-02-19', 'O+', 188.00, 95.00, '9876543212', 'bruce.wayne@waynecorp.com', 'Wayne Manor, Gotham City', '9999999999', 'None', 'Knee strain', 'Wayne Insurance Corp'),
(2, 'PAT-10002', 5, 'Clark Kent', 35, 'MALE', '1991-06-18', 'A+', 190.00, 102.00, '9876543213', 'clark.kent@dailyplanet.com', '321 Maple St, Metropolis', '8888888888', 'Lead, Kryptonite', 'None', 'Daily Planet Health Plan'),
(3, 'PAT-10003', 6, 'Diana Prince', 30, 'FEMALE', '1996-03-22', 'AB+', 178.00, 72.00, '9876543214', 'diana.prince@museum.org', '1000 Gateway Blvd, Gateway City', '7777777777', 'None', 'Shoulder dislocation', 'Global Shield Insurance');

-- Seed Appointments
INSERT INTO appointments (id, appointment_code, patient_id, doctor_id, department, appointment_dt, reason, status) VALUES
(1, 'APT-10001', 1, 1, 'ORTHOPEDICS', '2026-07-15 10:30:00', 'Routine checkup for knee injury recovery', 'COMPLETED'),
(2, 'APT-10002', 2, 2, 'PHYSIOTHERAPY', '2026-07-16 14:00:00', 'Lower back rehabilitation therapy', 'CONFIRMED'),
(3, 'APT-10003', 3, 1, 'ORTHOPEDICS', '2026-07-18 11:00:00', 'Severe shoulder joint pain', 'PENDING');

-- Seed Medical Records
INSERT INTO medical_records (id, patient_id, record_date, title, description, created_by) VALUES
(1, 1, '2026-07-15', 'Knee Arthroscopy Post-Op check', 'Patient shows good recovery progress. Swelling has reduced by 80%. Recommended range of motion exercises.', 2),
(2, 3, '2026-07-17', 'Initial Shoulder Consultation', 'Acute shoulder dislocation pain. Standard X-ray taken. Joint is stable but inflamed. Prescribed pain relief and relative rest.', 2);

-- Seed Prescriptions
INSERT INTO prescriptions (id, appointment_id, patient_id, doctor_id, prescribed_date, notes) VALUES
(1, 1, 1, 1, '2026-07-15', 'Continue rehabilitation therapy twice a week.'),
(2, 3, 3, 1, '2026-07-18', 'Avoid heavy lifting for 2 weeks.');

-- Seed Prescription Items
-- For Prescription 1
INSERT INTO prescription_items (id, prescription_id, medicine_id, dosage, morning, afternoon, night, duration_days, instructions) VALUES
(1, 1, 2, '400mg', TRUE, FALSE, TRUE, 7, 'Take after meals'),
(2, 1, 3, '1 tab', TRUE, FALSE, FALSE, 30, 'Take in the morning');

-- For Prescription 2
INSERT INTO prescription_items (id, prescription_id, medicine_id, dosage, morning, afternoon, night, duration_days, instructions) VALUES
(3, 2, 1, '500mg', TRUE, TRUE, TRUE, 5, 'For pain, only as needed');

-- Seed Bills
INSERT INTO bills (id, bill_number, patient_id, appointment_id, total_amount, gst_amount, discount, net_amount, status) VALUES
(1, 'BILL-10001', 1, 1, 500.00, 90.00, 50.00, 540.00, 'PAID'),
(2, 'BILL-10002', 2, 2, 400.00, 72.00, 0.00, 472.00, 'UNPAID');

-- Seed Payments
INSERT INTO payments (id, bill_id, amount, method, reference_id) VALUES
(1, 1, 540.00, 'UPI', 'UPI-REF-99281928');
