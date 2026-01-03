FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

# ✅ FIX permission issue
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
