FROM eclipse-temurin:17-jdk

EXPOSE 8081

WORKDIR /root

COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN ./mvnw dependency:go-offline
COPY ./src /root/src

RUN ./mvnw clean install

ENTRYPOINT ["java","-jar","/root/target/cuenta-ms-0.0.1-SNAPSHOT.jar"]
