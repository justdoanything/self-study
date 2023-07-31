FROM openjdk:11-jre-slim
EXPOSE 8080
COPY build/libs/searching-0.0.1.jar searching.jar
ENTRYPOINT ["java", "-jar", "/searching.jar"]