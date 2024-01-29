FROM openjdk:17-jdk-slim

WORKDIR /data

COPY build/libs/*.jar /data/app.jar

ENTRYPOINT ["java","-Xms512m","-Xmx4096m","-XX:+UseG1GC","-jar","-Duser.language=zh","/data/app.jar"]
