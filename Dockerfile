# Use an official Java 17 runtime as a parent image
FROM eclipse-temurin:17-jre

# Set the working directory in the container
WORKDIR /data

# Copy the jar file into the container at /data
COPY target/dropwizard-SNAPSHOT.jar /data/dropwizard.jar

# Copy the configuration file into the container at /data
COPY src/main/resources/docker-config.yml /data/docker-config.yml

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Create a directory for logs
RUN mkdir /logs

# Mount the logs directory as a volume
VOLUME /logs

# Run the jar file
ENTRYPOINT ["java", "-jar", "/data/dropwizard.jar", "server", "/data/docker-config.yml"]