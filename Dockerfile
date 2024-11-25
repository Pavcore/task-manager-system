FROM maven:3.8.5 AS build
LABEL authors="Pavel Korchagin"
WORKDIR /tmp
COPY . /tmp
RUN mvn -f /tmp/pom.xml clean package -DskipTests

FROM openjdk:21-jdk
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:6432/postgres
WORKDIR /app
COPY --from=build /tmp/target/Task-Management-System-1.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/Task-Management-System-1.jar", "--server.port=8080"]