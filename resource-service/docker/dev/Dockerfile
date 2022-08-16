FROM openjdk:17-oracle

COPY build/libs/resource-service.jar /app/resource-service.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","resource-service.jar"]