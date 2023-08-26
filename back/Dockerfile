FROM openjdk:17-jdk-alpine
ARG JAR_NAME
COPY build/libs/${JAR_NAME}.jar bff.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx128m", "-XX:+UseSerialGC", "-verbose:gc", "-jar", "bff.jar"]