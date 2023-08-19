FROM openjdk:17-jdk

WORKDIR /app

COPY target/api-validator.jar api-validator.jar

EXPOSE 8080

CMD ["java", "-jar", "api-validator.jar"]
