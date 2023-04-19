FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./service_statistics-0.0.1-SNAPSHOT.jar ./service_statistics.jar
EXPOSE 8008
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m"]
CMD ["service_statistics.jar"]
