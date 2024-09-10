####
# This Dockerfile is used in order to build a container that runs the Spring Boot application
#
# Before building the docker image run:
#
# mvn package
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile -t opicarelli/abinbevchallenge .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 opicarelli/abinbevchallenge
#
###
FROM openjdk:14
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]