
# used to dockerize the application

# Use a Java 21 base image 
FROM openjdk:21-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local system into the container (change the path if necessary)
ARG JAR_FILE=./target/exchange-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expose the application port (default is 8080 for Spring Boot apps)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
