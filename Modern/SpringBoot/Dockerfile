FROM openjdk:11-jre-slim
EXPOSE 8080
COPY build/libs/modern-0.0.1.jar modern.jar
ENTRYPOINT ["java", "-jar", "/modern.jar"]