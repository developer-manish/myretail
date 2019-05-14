FROM java:8-jdk-alpine

COPY ./target/myretail-1.0.0-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","myretail-1.0.0-SNAPSHOT.jar"]  