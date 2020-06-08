# Example for a multi stage docker.

# In first stage we will build the project an dcreate a JAR file.

#Starts from the Gradle image
FROM gradle:jdk14 AS build
#Copies the Java source code inside the container
COPY . /home/gradle/src
#Compiles the code and runs unit tests (with Gradle build)
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

#Create an image Using the openjdk image.
FROM openjdk:14.0-slim
#<HOST_PORT>:<CONTAINER_PORT>
EXPOSE 8084:8084
RUN mkdir /app
#Copy the Jar that was build in build stage to /app/spring-boot-docker-poc.jar
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-docker-poc.jar
#Run the below command to start the container.
ENTRYPOINT ["java", "-jar","/app/spring-boot-docker-poc.jar"]