FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/AGTW0-0.0.1-SNAPSHOT.jar /app/AGTW0.jar

# Expose the port
EXPOSE 8079
  
LABEL name="agtw-img"

# Run the application
CMD ["java", "-jar", "AGTW0.jar"]

