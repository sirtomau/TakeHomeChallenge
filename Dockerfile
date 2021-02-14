FROM adoptopenjdk:11-jre-openj9
RUN mkdir /opt/app
COPY target/hireme-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 5000
CMD ["java", "-jar", "/opt/app/hireme-0.0.1-SNAPSHOT.jar"]