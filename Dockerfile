FROM openjdk:17
COPY build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app.jar"]
