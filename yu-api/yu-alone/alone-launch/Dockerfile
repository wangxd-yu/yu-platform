FROM openjdk:8-jdk-alpine

ADD target/alone-launch-0.0.1-SNAPSHOT.jar /alone-launch.jar

VOLUME /yu/api/alone-launch/config
VOLUME /yu/api/alone-launch/logs

EXPOSE 1128

# 解决中文乱码问题
ENV LANG C.UTF-8

# 修改时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/alone-launch.jar"]