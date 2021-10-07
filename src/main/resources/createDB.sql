CREATE SCHEMA `igadi` DEFAULT CHARACTER SET utf8 ;

CREATE USER 'igadiAdmin'@'localhost' IDENTIFIED BY 'igadiPassword';
GRANT ALL PRIVILEGES ON igadi.* TO 'igadiAdmin'@'localhost';