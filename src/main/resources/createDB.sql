DROP SCHEMA IF EXISTS Igadi;

CREATE SCHEMA Igadi DEFAULT CHARACTER SET utf8;

USE Igadi;

CREATE USER 'igadiAdmin'@'localhost' IDENTIFIED BY 'igadipassword';
DROP USER IF NOT EXISTS 'igadiAdmin'@'localhost'@'localhost';
GRANT ALL PRIVILEGES ON Igadi.* TO 'igadiAdmin'@'localhost';
