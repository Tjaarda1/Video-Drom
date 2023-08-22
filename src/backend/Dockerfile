FROM maven AS build-stage
WORKDIR /usr/src
COPY . .
RUN mvn package -f ./pom.xml

FROM eclipse-temurin:17 AS production-stage
WORKDIR /opt/app
COPY --from=build-stage /usr/src/target/backend.jar .
EXPOSE 8081
CMD ["java", "-jar", "/opt/app/backend.jar"]