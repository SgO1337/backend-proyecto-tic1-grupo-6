# Stage 1: Build stage using OpenJDK 21
FROM openjdk:21-jdk AS build
WORKDIR /app

# Copy necessary files
COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

# Set execution permission and build the application
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:21-jdk
WORKDIR /app
VOLUME /tmp

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 9090