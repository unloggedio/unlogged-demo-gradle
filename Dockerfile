FROM eclipse-temurin:17-jdk-jammy

WORKDIR .
COPY .gradle/ .gradle
COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle ./
COPY ./src ./src
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./gradlew
CMD ["./gradlew", "bootrun"]