# Person Management Application #
Repository has below projects 

**1 : person-management-api** : Rest API 

*[JAVA 8 + Spring Boot + JPA + H2 + Spring Security + JWT + mapstruct + junit 5]*

**2 : person-management-api-reactive** : Non Blocking Reactive API 

*[JAVA 8 + Spring Boot + Spring Webflux + reactivemongo + Docker + Docker Compose + junit 5]*                                      

**3 : UI** - WIP


 ### person-management-api ###  
 **[JAVA 8 + Spring Boot + JPA + H2 + Spring Security + JWT + mapstruct + junit 5]**  
*Security* : All Rest endpoints are secured using JWT. Role based access control is implemented on rest endpoints, only Valid user with "ADMIN" Role can perform delete operation.

*persistence* : H2 In-memory database is configured to store data. Database operations are performed using JPA.   

*Monitoring* : Actuator health endpoint can be used to track status.Build and git commit information is exposed in info endpoint to check deployed version

*Documentation*: API Contract can be checked by using swagger.

*Testing*: Code is supported with lot of unit and integration test cases.Whenever possible junit 5 Parameterized Test are used. 

**Build and run the app using maven**

```bash
cd person-management-api
mvn package
java -jar target/person-management-api-0.0.1-SNAPSHOT.jar
OR 
mvn spring-boot:run
```
The server will start at http://localhost:8080.

Health endpoint : http://localhost:8080/actuator/health

Info ednpoint : http://localhost:8080/actuator/info

Swagger http://localhost:8080/swagger-ui.html#

**Users** : Two below Test uses are configured

user Name : niraj.sonawane@gmail.com , password : test ,role - ADMIN

user Name : test@gmail.com , password : test ,role - USER

Sample Curl to get Token 
```
curl -X POST \
  http://localhost:8080/authenticate \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5e77f8cd-97bb-4faa-b1fa-3f099b4ad58c' \
  -H 'cache-control: no-cache' \
  -d '{"userName":"niraj.sonawane@gmail.com","password":"test"}'
  ```
  
### person-management-api-reactive ###  

*[JAVA 8 + Spring Boot + Spring Webflux + reactivemongo + Docker + Docker Compose + junit 5]*                                      
 
*Reactive Implementation* : All Rest endpoints are non-blocking and build on webflux. 

*persistence* : Dockerized mongodb image is configured to store data. Database operations are performed using reactivemongo to get non-blocking behaviour.   

*Cloud Native* : Application is containerized using docker & docker composed is used to run this multi container api.  

*Monitoring* : Actuator health endpoint can be used to track status.Build and git commit information is exposed in info endpoint to check deployed version

*Documentation*: API Contract can be checked by using swagger.

*Testing*: Code is supported with lot of unit and integration test cases.Whenever possible junit 5 Parameterized Test are used. 

*Security*: endpoints are not secured, but can be implemented on similarly how we have implemented for non-reactive api   

**Build and run the app using maven** 

maven is also configured to create docker image so no separate docker build command is needed for creating docker image.   

Note- Ports are configured in docker-compose file. 

```bash
cd person-management-api-reactive
mvn package
//it will build the project and will create docker image

docker-compose up   -> it will start 2 containers one for api and one for mongodb 

```
The server will start at http://localhost:8081.

Health endpoint : http://localhost:8081/actuator/health

Info ednpoint : http://localhost:8081/actuator/info

Swagger http://localhost:8081/swagger-ui.html#

