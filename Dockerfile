# Use the official OpenJDK as a parent image
FROM eclipse-temurin:21-jdk

# Set the maintainer label
LABEL maintainer="udaykumar"

# Copy the JAR file from the target directory to the /app directory in the container
COPY target/Student-0.0.1-SNAPSHOT.jar /app/student-app.jar

# Set the working directory
WORKDIR /app

# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "student-app.jar"]
