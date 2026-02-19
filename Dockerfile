FROM node:20 AS frontend-builder
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

FROM maven:3.9.9-eclipse-temurin-21 AS backend-builder
WORKDIR /app/backend

COPY backend/pom.xml .
RUN mvn dependency:go-offline

COPY backend/ .

COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=backend-builder /app/backend/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
