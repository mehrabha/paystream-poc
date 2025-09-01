FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# JAR already built by CodeBuild host; just copy it in
COPY api-service/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
