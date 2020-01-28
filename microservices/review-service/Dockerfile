FROM openjdk
EXPOSE 8080
MAINTAINER Serhey Doroshenko
WORKDIR /opt/review-service
ADD build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java","-jar","app.jar"]