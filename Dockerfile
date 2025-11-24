#
# Build stage
#
FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean
RUN gradle bootJar --no-daemon

#
# Package stage
#
FROM eclipse-temurin:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["java","-jar","/app.jar"]