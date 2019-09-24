FROM gradle:jdk12 as builder
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true"
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM adoptopenjdk:12-jre-hotspot
RUN mkdir /opt/app
COPY --from=builder /home/gradle/src/build/libs/twitter-publish-automator-*.*.*-SNAPSHOT.jar /opt/app/app.jar
VOLUME /opt/app/tweet/store/
ENTRYPOINT [ "java", "-jar", "/opt/app/app.jar", "--spring.profiles.active=docker" ]