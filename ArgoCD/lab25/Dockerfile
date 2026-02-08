FROM maven:3.9.5-eclipse-temurin-17
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=0 /app/target/demo-0.0.1-SNAPSHOT.jar ./demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]

