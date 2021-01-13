# project
This is a users management system

## How to setup
  To setup this project you can follow one of the two following methods
  
### 1) By cloning and executing the project

Start by cloning project with
```
git clone https://github.com/robvan2/project.git
```

#### a) Open project with your Idea (must have Maven too)
For this readme i'am assuming you're using intellij. Wait for Maven to resolve all the dependencies then :

#### b) Add Lombok plugin to your Idea:
this link shows you how to [Lombok Intellij](https://projectlombok.org/setup/intellij).

#### c) Ready to go:
Navigate to ProjectApplication and Run It.

### 2) By downloading compiled version :

- Start By downloading project (.jar file) [Project Drive](https://drive.google.com/file/d/1K1WzQ3jJUUZrGm1PVxHFbiXPVAS_m-FR/view?usp=sharing).

- Open your terminal and navigate to the folder in which you saved the project.

- Run the following command :
```
java -jar project-0.0.1-SNAPSHOT.jar
```

## Documentaion :
  This section familiarizes you with the project's features.
  
#### Swagger :
The project uses [Swagger ui](https://swagger.io/tools/swagger-ui/). which contains full roadmap of the project APIs (routes + Dtos to privide (request body) + Response).
Use the following link to access 
```
http://localhost:8080/swagger-ui.html
```

#### H2 Database :
It's an in memory database. To access the console use `http://localhost:8080/h2-console/`,
then use username `recruitment` and passsword `563123` (also jdbc url `jdbc:h2:mem:recruitment`).
You can change that if you setup project with the 1st method by changing it in application.properties

#### Notes :
- For authentification I used jwt (/api/checkToken with "Authorization" header (Bearer token) checks the validity of token and /api/logout to invalidate token)
