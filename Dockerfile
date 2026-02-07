# Etapa 1: Construccion
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecucion
FROM eclipse-temurin:21-jdk
WORKDIR /app
# El nombre se construye con artifactId + version del pom.xml
COPY --from=build /app/target/network-inventory-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
# Forzamos a Java a usar el puerto 8081 al arrancar
ENTRYPOINT ["java", "-Dserver.port=8081", "-jar", "app.jar"]