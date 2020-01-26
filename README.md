# Person Management Application #
Repository has below projects 

**1 : person-management-api** : Rest API For CURD 

*[JAVA 8 + Spring Boot + JPA + H2 + Spring Security + JWT + mapstruct + junit 5]*

**2 : person-management-api-reactive** : Reactive Version Of Same API  

*[JAVA 8 + Spring Boot + Spring Webflux + reactivemongo + Docker + Docker Compose + junit 5]*                                      

**3 : person-management-ui-angular** - User Interface for Performing CURD Operations
*[Angular 7 + material design + reactive API as backend]*

**4 : .github** Github build pipeline configurations  

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
$ curl -X POST http://localhost:8080/authenticate -H "Content-Type:application/json" -d '{"userName":"niraj.sonawane@gmail.com","password":"test"}'
  ```
To get Person  
```
curl -X GET http://localhost:8080/person -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBUEkgQVBQIiwic3ViIjoibmlyYWouc29uYXdhbmVAZ21haWwuY29tIiwiaWF0IjoxNTc5ODEzODk5LCJleHAiOjE1Nzk4NjM4OTksIlJvbGVzIjpbIlJPTEVfQURNSU4iXX0.z02f5X9rBJA5uIUcLCc2rRi11_F2wc75nKRSqS1bqaWI5g2TXoMmeqZp3C56NdUPv6TsuipfbB2R7rC6_OzSOg"
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

maven is also configured to **create docker image** so no separate docker build command is needed for creating docker image.   

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


### person-management-ui-angular ### 

`cd person-management-ui-angular`

`npm install`  ---To Install package

`ng serve`    --- To Start Application

Application Will be started at http://localhost:4200/person


### TODO ###
1 : ~~Create Single build for all All Three Projects~~ Done

2 : ~~Dockerized the UI Application~~ Done

3 : ~~Create docker-compose file for complete application~~ Done

### To Build complete application  ###
run `mvn package` in roor directory (person-management-curd-system)

maven will build all modules (`person-management-api`,`person-management-api-reactive` and `person-management-ui-angular`)

maven build will also create docker images for `person-management-api-reactive` and `person-management-ui-angular`

### To Run complete application [UI + Reactive API + Mongo Database in docker]  ###

run `docker-compose up` in roor directory (person-management-curd-system) and access the application http://localhost:4200/
