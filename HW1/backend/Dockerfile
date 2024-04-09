FROM maven:3.9-eclipse-temurin-21-alpine

WORKDIR /backend

COPY . .

RUN mvn install -DskipTests

CMD mvn spring-boot:run