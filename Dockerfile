FROM adoptopenjdk/openjdk11:alpine-jre
# defines a variable that can be passed in at build time, in this case to provide the name of the Spring Boot jar file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]