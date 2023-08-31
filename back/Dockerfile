FROM openjdk:17-jdk-alpine
ARG JAR_NAME
COPY build/libs/${JAR_NAME}.jar bff.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-Xms64m", "-Xmx64m", "-Xss1m", "-XX:+UseSerialGC", "-jar", "bff.jar"]