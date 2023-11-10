# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/KaddemProject-1.0.jar app.jar

# Expose the port that the application will run on
EXPOSE 8089

# Define the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]
