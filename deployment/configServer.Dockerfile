# Build stage
FROM openjdk:17 AS build
WORKDIR /workspace
COPY ..
RUN ./gradlew build

# Deploy stage
FROM openjdk:17
COPY --from=build /workspace/build/libs/config-server-0.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]