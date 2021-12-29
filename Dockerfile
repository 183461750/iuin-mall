#FROM openjdk:8-jdk-alpine
FROM hub.c.163.com/dwyane/openjdk:8
VOLUME /tmp
WORKDIR /workdir
ARG JAR_FILE
ARG APP_PORT
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE ${APP_PORT}