FROM eclipse-temurin:25-jre

LABEL maintainer="Joel Anderson Fernandez Pancorvo"

WORKDIR /app

COPY build/libs/bankapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]