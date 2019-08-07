# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine

# application placed into /opt/app
RUN mkdir -p /opt/app
WORKDIR /opt/app

# copy dependencies/config, etc.
COPY * /opt/app/

# start application
CMD ["java", "-jar", "todos-rest-1.0.0-SNAPSHOT.jar", "server", "config.yml"]

