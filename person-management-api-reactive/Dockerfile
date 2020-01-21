FROM openjdk:8-jdk-alpine
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/person-management-api-reactive-0.0.1-SNAPSHOT.jar"]
ADD target/person-management-api-reactive-0.0.1-SNAPSHOT.jar  /usr/share/myservice/lib
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/person-management-api-reactive-0.0.1-SNAPSHOT.jar