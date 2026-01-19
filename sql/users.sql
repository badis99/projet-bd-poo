-- Creation of the application user
CREATE USER IF NOT EXISTS 'pharmacie_user'@'localhost' IDENTIFIED BY 'pharmacie_pass';

-- Creation of the database
CREATE DATABASE IF NOT EXISTS pharmacie_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Granting permissions
GRANT ALL PRIVILEGES ON pharmacie_db.* TO 'pharmacie_user'@'localhost';
FLUSH PRIVILEGES;
