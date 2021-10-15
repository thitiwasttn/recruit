FROM thitiwas007007/java11:latest

ENV TZ=Asia/Bangkok

RUN mkdir /root/file && mkdir /root/app

COPY app.jar /root/app/

CMD ["java", "-jar", "/root/app/app.jar"]
