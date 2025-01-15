# Usa una imagen base con OpenJDK 21
FROM openjdk:21-jdk-slim AS build

# Instala dependencias necesarias para ejecutar mvnw (curl, bash)
RUN apt-get update && apt-get install -y curl bash

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo pom.xml y mvnw al contenedor
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn

# Copia el código fuente al contenedor
COPY src ./src

# Da permisos de ejecución al archivo mvnw
RUN chmod +x mvnw

# Ejecuta el comando mvn clean package usando el wrapper para generar el archivo .jar
RUN ./mvnw clean package -DskipTests


# Ahora crea una imagen separada para el contenedor final
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo en el contenedor final
WORKDIR /app

# Copia el archivo .jar generado desde el contenedor build
COPY --from=build /app/target/ms-books-catalogue-0.0.1-SNAPSHOT.jar ms-books-catalogue.jar

# Expone el puerto
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "ms-books-catalogue.jar"]