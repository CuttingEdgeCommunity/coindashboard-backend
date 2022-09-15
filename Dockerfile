FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app.jar
RUN --mount=type=secret,id=app.key
ENTRYPOINT ["java","-jar","./app.jar"]
