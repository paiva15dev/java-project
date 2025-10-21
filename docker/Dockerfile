# Usa uma imagem base com Java 17+
FROM eclipse-temurin:21-jdk-jammy

# Define o argumento que aponta para o .jar (um nível acima)
ARG JAR_FILE=../target/*.jar

# Copia o .jar do diretório pai para dentro do container
COPY ${JAR_FILE} app.jar

# Expõe a porta 8080 (Spring Boot padrão)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]
