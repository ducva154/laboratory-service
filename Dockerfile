FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
COPY ${JAR_FILE} laboratory-management-service.jar
ENTRYPOINT ["java","-jar","laboratory-management-service.jar"]
