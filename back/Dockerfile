FROM openjdk:17-jdk-alpine
ARG JAR_NAME
COPY build/libs/${JAR_NAME}.jar bff.jar
EXPOSE 8080
EXPOSE 7203
ENTRYPOINT [ "java", "-Xmx128m", "-XX:+UseSerialGC", "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.port=7203", "-Dcom.sun.management.jmxremote.rmi.port=7203", "-Djava.rmi.server.hostname=0.0.0.0", "-jar", "bff.jar"]