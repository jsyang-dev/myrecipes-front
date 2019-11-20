FROM openjdk:8-jdk-slim
ENV PORT 8080
EXPOSE 8080

COPY *.jar app.jar
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "app.jar"]