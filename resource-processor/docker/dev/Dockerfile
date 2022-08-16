FROM openjdk:17-oracle

COPY ./build/libs/resource-processor.jar /app/resource-processor.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","resource-processor.jar"]