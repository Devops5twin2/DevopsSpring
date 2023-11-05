# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
RUN curl -o app.jar -u admin:admin http://192.187.47.10:8081/repository/maven-releases/tn/esprit/KaddemProject/1.0/KaddemProject-1.0.jar

# Expose the port that the application will run on
EXPOSE 9090

# Define the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]
