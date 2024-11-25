FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

# Copy the Maven Wrapper files and make mvnw executable
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw

COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Create the runtime image
FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
