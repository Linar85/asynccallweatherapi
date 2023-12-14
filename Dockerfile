FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/asynccallweatherapi-0.0.1-SNAPSHOT.jar .
COPY docker-startup.sh .
RUN chmod +x docker-startup.sh
EXPOSE 8090
CMD ["./docker-startup.sh"]