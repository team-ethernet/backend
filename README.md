# backend
Repository for the backend implementation.

The REST API documentation can be found [here.](/src/main/java/teamethernet/rest_api/README.md)

## Project code needs
* [MySQL](https://dev.mysql.com/downloads/)

## Initial setup
1. Make sure a MySQL server is running on port 3306 (username: root, password: root)
2. Create a MySQL database named noisyIoT using SQL query `CREATE DATABASE noisyIoT;`
3. Change the first line of application.propeties in "src\main\resources\" to `spring.jpa.hibernate.ddl-auto=create`
4. Change mqtt.properties in "src\main\resources\" to the IP, port and topic of your MQTT publisher.
5. Build the application according to the build instructions below (make sure you are in the backend folder)
6. The program is now running on localhost:8080
7. After the first build, change the first line of application.propeties back to `spring.jpa.hibernate.ddl-auto=none`

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
Erik Flink    
Isak Olsson   
Nelly Friman  
Anton Bothin     
Andreas Sjödin  
Jacob Klasmark    
Carina Wickström  
Valter Lundegårdh   
