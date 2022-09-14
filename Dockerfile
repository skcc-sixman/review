FROM openjdk:17
COPY build/libs/*SNAPSHOT.jar review.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prd", "/review.jar"]
