# Person Management Application #
Repository has below projects 

**1 : person-management-api** : Rest API [Backend] 

**2 : person-management-api-reactive** : Non Blocking Reactive API [JAVA 8 + Spring Boot + Spring Webflux + reactivemongo + Docker + Docker Compose + junit 5]  on-management-curd-system                                      

**3 : UI** - WIP


 ### person-management-api [Backend] ###  [JAVA 8 + Spring Boot + JPA + H2 + Spring Security + JWT + junit 5]  
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

or 
mvn spring-boot:run
```
