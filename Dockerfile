# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

RUN curl -o app.jar -u admin:nexus http://192.168.33.10:8081/repository/maven-releases/tn/esprit/KaddemProject/1.0/KaddemProject-1.0.jar

# Expose the port that the application will run on
EXPOSE 9091

# Define the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]
