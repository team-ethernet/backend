# team-ethernet.web
Repository for the web page

## Dependencies
* [Spring](https://start.spring.io/)
* [MySQL](https://dev.mysql.com/downloads/)

## Good things to know

HTML files should go under team-ethernet.web\src\main\resources\templates

Images should go under team-ethernet.web\src\main\resources\static\images

## Getting started with the database
1. Make sure a MySQL server is running on port 3306
2. Create a MySQL database named noisyIoT, following command works through cmd: CREATE DATABASE noisyIoT;
3. Change the first line of application.propeties in "team-ethernet.web\src\main\resources\" to spring.jpa.hibernate.ddl-auto=create
4. Run the program in cmd (make sure you are in the team-ethernet.web folder) with the command: mvnw spring-boot:run
5. The program is now running on localhost:8080
6. After the first run change back the first line of application.propeties to spring.jpa.hibernate.ddl-auto=none
