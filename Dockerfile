FROM openjdk:17
COPY build/libs/syano_url_shortener-0.1-all.jar syano_url_shortener-0.1-all.jar
EXPOSE 8080
EXPOSE 50051
ENTRYPOINT ["java", "-jar", "syano_url_shortener-0.1-all.jar"]