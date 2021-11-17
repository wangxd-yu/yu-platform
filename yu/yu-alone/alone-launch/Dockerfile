FROM openjdk:8-jdk-alpine

ADD target/alone-launch-0.0.1-SNAPSHOT.jar /alone-launch.jar

VOLUME /yu/api/alone-launch/config

EXPOSE 1128

# 解决中文乱码问题
ENV LANG C.UTF-8

ENTRYPOINT ["java","-jar","/alone-launch.jar"]