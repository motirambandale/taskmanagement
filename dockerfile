# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-alpine

# Copy the application's jar file into the container
ADD target/taskmanagement.jar taskmanagement.jar

# Expose port 7777 for the application
EXPOSE 7777

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "taskmanagement.jar"]
