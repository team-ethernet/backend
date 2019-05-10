# team-ethernet.web
Repository for the server

## Project code needs
* [Spring](https://start.spring.io/)
* [MySQL](https://dev.mysql.com/downloads/)

## Initial setup
1. Make sure a MySQL server is running on port 3306 (username: root, password: root)
2. Create a MySQL database named noisyIoT using SQL query `CREATE DATABASE noisyIoT;`
3. Change the first line of application.propeties in "team-ethernet.web\src\main\resources\" to `spring.jpa.hibernate.ddl-auto=create`
4. Build the application according to the build instructions below (make sure you are in the team-ethernet.web folder)
5. The program is now running on localhost:8080
6. After the first build change the first line of application.propeties back to spring.jpa.hibernate.ddl-auto=none

## Build

### Windows
```
$mvnw spring-boot:run
```
### MAC
```
$brew install maven
$mvn spring-boot:run
```
### Linux
```
$sudo apt-get install maven
$mvn spring-boot:run
```
## Authors
Anton Bothin

Erik Flink  
Nelly Friman  
Jacob Klasmark  
Valter Lundegårdh  
Isak Olsson  
Andreas Sjödin  
Carina Wickström
