# Use the official Spring Boot image as a parent image
FROM arm64v8/openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar to the working directory
COPY target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]