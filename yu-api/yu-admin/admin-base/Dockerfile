FROM openjdk:8-jdk-alpine

ADD target/admin-base-0.0.1-SNAPSHOT.jar /admin-base.jar

VOLUME /yu/api/admin-base/config
VOLUME /yu/api/admin-base/logs

EXPOSE 1128

# 解决中文乱码问题
ENV LANG C.UTF-8

# 修改时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/admin-base.jar"]