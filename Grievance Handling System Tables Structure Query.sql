CREATE DATABASE ghs_db;
USE ghs_db;

-- 1. Table for Roles (Admin, Manager, User)
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. Table for Users
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- 3. Table for Grievances
CREATE TABLE grievances (
    g_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Pending', 'In-Progress', 'Resolved') DEFAULT 'Pending',
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Initial Roles as per your description
INSERT INTO roles (role_name) VALUES ('Admin'), ('Employee'), ('Grievance Manager');

-- 1. Disable the safety checks
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Truncate your tables (they will reset to 1 now)
TRUNCATE TABLE users;
TRUNCATE TABLE grievances;

-- 3. Re-enable the safety checks
SET FOREIGN_KEY_CHECKS = 1;