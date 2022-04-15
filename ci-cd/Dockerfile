FROM hub.predic8.de/p8/java17:3
COPY build/libs/*0.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/app.jar"]