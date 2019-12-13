FROM openjdk:11-jdk-slim
ENV PORT 8080
EXPOSE 8080

COPY target/*.jar app.jar
CMD ["java", "-XX:+UseG1GC", "-jar", "app.jar"]