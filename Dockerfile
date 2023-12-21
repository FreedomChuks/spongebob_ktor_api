FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11.0.16
EXPOSE 8080:8080
RUN mkdir /app
COPY  --from=build /src/build/libs/*.jar /app/spongebob_api.jar
ENTRYPOINT ["java","-jar","/app/spongebob_api.jar"]