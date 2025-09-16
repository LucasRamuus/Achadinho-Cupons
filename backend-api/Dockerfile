# Etapa 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências para cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o JAR buildado
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta (mesma do application.properties)
EXPOSE 8080

# Comando padrão
ENTRYPOINT ["java","-jar","app.jar"]
