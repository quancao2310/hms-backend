# Stage: base
FROM eclipse-temurin:21-jdk-alpine AS base
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw* pom.xml ./
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests

# Stage: dev
FROM eclipse-temurin:21-jdk-alpine AS dev
RUN apk update && apk add curl
WORKDIR /app
EXPOSE 8888
COPY --from=base /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Stage: prepare-production
FROM bellsoft/liberica-openjre-debian:21-cds AS prepare-production
WORKDIR /builder
COPY --from=base /app/target/*.jar app.jar
# Extract the jar file using an efficient layout
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

# Stage: production
FROM bellsoft/liberica-openjre-debian:21-cds AS production
RUN apk update && apk add curl
WORKDIR /app
EXPOSE 8888
# Copy the extracted jar contents from the prepare container into the working directory in the runtime container
ARG EXTRACTED=/builder/extracted
COPY --from=prepare-production ${EXTRACTED}/dependencies/ ./
COPY --from=prepare-production ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=prepare-production ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=prepare-production ${EXTRACTED}/application/ ./
# Execute the CDS training run
RUN java -XX:ArchiveClassesAtExit=application.jsa -Dspring.context.exit=onRefresh -jar app.jar
# Start the application jar with CDS enabled - this is not the uber jar used by the builder
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and CDS friendly
ENTRYPOINT ["java", "-XX:SharedArchiveFile=application.jsa", "-jar", "app.jar"]
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl --fail http://localhost:8888/actuator/health || exit 1
