FROM maven:3-openjdk-18 AS builder

COPY pom.xml pom.xml
RUN mvn -B dependency:go-offline

COPY src src
RUN mvn -B package

FROM openjdk:18-jdk-alpine

RUN addgroup -S app && adduser -S app -G app
WORKDIR /home/app
USER app

COPY --from=builder /target/oauth-server.jar oauth-server.jar

CMD ["java", "-jar", "/home/app/oauth-server.jar"]
