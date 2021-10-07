CREATE TABLE `igadi` DEFAULT CHARACTER SET utf8 ;

CREATE USER 'igadiadmin'@'localhost' IDENTIFIED BY 'igadipassword';
GRANT ALL PRIVILEGES ON igadi.* TO 'igadiadmin'@'localhost';

