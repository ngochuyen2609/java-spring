CREATE DATABASE java_spring  CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'java_spring '@'localhost' IDENTIFIED BY 'java@vietdefi';
GRANT ALL ON java_spring .* TO 'java_spring '@'localhost';
CREATE USER 'java_spring '@'%' IDENTIFIED BY 'java@vietdefi';
GRANT ALL ON java_spring .* TO 'java_spring '@'%';

