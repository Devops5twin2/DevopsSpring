# Use the official OpenJDK base image with a specific version
FROM openjdk:11-jre-slim-buster

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
ADD http://192.168.1.178:8081/repository/maven-releases/tn/esprit/KaddemProject/1.0/KaddemProject-1.0.jar app.jar

# Expose the port that the application will run on
EXPOSE 8089

# Define the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]

