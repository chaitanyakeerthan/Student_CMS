# Build stage
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built JAR and rename it to app.jar
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
