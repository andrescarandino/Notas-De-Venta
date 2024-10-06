FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/notas_ventas-0.0.1.jar
COPY ${JAR_FILE} app_notas_ventas.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_notas_ventas.jar"]