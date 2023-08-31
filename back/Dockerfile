FROM bellsoft/liberica-runtime-container:jre-17-slim-musl
ARG JAR_NAME
COPY build/libs/${JAR_NAME}.jar bff.jar
EXPOSE 8080
EXPOSE 9010
ENTRYPOINT java \
    -Xms64m \
    -Xmx64m \
    -Xss1m \
    -XX:+UseSerialGC \
    -Dcom.sun.management.jmxremote \
    -Dcom.sun.management.jmxremote.local.only=false \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.port=9010 \
    -Dcom.sun.management.jmxremote.rmi.port=9010 \
    -Djava.rmi.server.hostname=0.0.0.0 \
    -Dcom.sun.management.jmxremote.ssl=false \
    -jar \
    bff.jar