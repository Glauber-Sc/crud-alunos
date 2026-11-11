# Gradle fica dentro do Docker: nada para instalar na máquina além do Docker.
FROM gradle:8.14.3-jdk21 AS build
USER root
RUN mkdir -p /home/gradle/projeto && chown -R gradle:gradle /home/gradle/projeto
USER gradle
WORKDIR /home/gradle/projeto
COPY --chown=gradle:gradle build.gradle settings.gradle ./
COPY --chown=gradle:gradle src ./src
RUN gradle --no-daemon clean test bootJar

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN groupadd --system app && useradd --system --gid app app \
    && mkdir -p /app/data && chown -R app:app /app
COPY --from=build /home/gradle/projeto/build/libs/*.jar /app/app.jar
USER app
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
