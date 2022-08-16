FROM openjdk:17-oracle

COPY build/libs/song-service.jar /app/song-service.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","song-service.jar"]