FROM maven:3.9.6-eclipse-temurin-17 AS build
ENV PORT=8081
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

# Second stage: create a slim image
FROM eclipse-temurin:17
ENV PORT=8080
COPY --from=build /app/target/booking-api-0.0.1-SNAPSHOT.jar /app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar", "-Dserver.port=Integer.parseInt($PORT)"]
# https://stackoverflow.com/questions/37904682/how-do-i-use-docker-environment-variable-in-entrypoint-array
ENTRYPOINT java -jar /app.jar --server.port=$PORT