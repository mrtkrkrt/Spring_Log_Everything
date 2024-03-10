FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests=true
FROM openjdk:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/logging-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "logging-0.0.1-SNAPSHOT.jar"]
#docker build -t review .